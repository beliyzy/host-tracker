package com.mint.hosttracker.url.service

import com.mint.hosttracker.url.domain.Url

interface UrlService {
    fun create(url: Url);

    fun update(url: Url);

    fun delete(id: Long);

    fun getById(id: Long): Url?;

    fun getAll(): List<Url>;

    fun process(url: Url);

    fun updateUrl(id: Long, url: Url)

    fun deleteUrl(id: Long)

    fun addUrl(id: Long, url: Url)

    fun cancelTask(id: Long)

    fun scheduleTask(id: Long, url: Url)
}