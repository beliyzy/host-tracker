package com.mint.hosttracker.url.service.impl

import com.mint.hosttracker.url.dao.UrlDAO
import com.mint.hosttracker.url.domain.Url
import com.mint.hosttracker.url.service.UrlService
import java.net.HttpURLConnection
import java.net.URL
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class SimpleUrlService(
    private var urlDAO: UrlDAO,
    private var idToUrl: MutableMap<Long, Url>,
    private var activeUrlTasks: MutableMap<Long, ScheduledFuture<*>>,
    private var executorService: ScheduledExecutorService
) : UrlService {

    override fun create(url: Url) {
        val createdUrl = urlDAO.create(url)
        if (createdUrl != null && isUrlValid(createdUrl)) {
            idToUrl[createdUrl.id!!] = createdUrl
            addUrl(createdUrl.id!!, createdUrl)
        }
    }

    override fun update(url: Url) {
        val updatedUrl = urlDAO.update(url)
        if (updatedUrl != null && isUrlValid(updatedUrl)) {
            idToUrl[updatedUrl.id!!] = updatedUrl
            updateUrl(updatedUrl.id, updatedUrl)
        }
    }

    override fun delete(id: Long) {
        val deletedId: Long? = urlDAO.delete(id);
        if (deletedId != null) {
            idToUrl.remove(deletedId)
            cancelTask(deletedId)
        }
    }

    override fun getById(id: Long): Url? = urlDAO.getById(id)

    override fun getAll(): List<Url> {
        return urlDAO.getAll()
    }

    override fun updateUrl(id: Long, url: Url) {
        cancelTask(id)
        scheduleTask(id, url)
    }

    override fun deleteUrl(id: Long) {
        cancelTask(id)
    }

    override fun addUrl(id: Long, url: Url) {
        scheduleTask(id, url)
    }

    override fun cancelTask(id: Long) {
        val task = activeUrlTasks.remove(id)
        task?.cancel(false)
    }

    override fun scheduleTask(id: Long, url: Url) {
        val task = executorService.scheduleAtFixedRate({
            try {
                process(url)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, url.period, url.period, TimeUnit.SECONDS)
        activeUrlTasks[id] = task
    }

    override fun process(url: Url) {
        val connection = URL(url.value).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val startTime = System.currentTimeMillis()
        val responseCode = connection.responseCode
        val responseTime = System.currentTimeMillis() - startTime

        url.status = responseCode.toString()
        url.pingTime = responseTime
        url.updatedAt = Timestamp(System.currentTimeMillis())
        update(url)
    }

    private fun isUrlValid(url: Url?): Boolean {
        return Objects.nonNull(url?.id) && Objects.nonNull(url?.period)
    }
}