package com.mint.hosttracker.url.config

import com.mint.hosttracker.url.dao.UrlDAO
import com.mint.hosttracker.url.dao.impl.SimpleUrlDAO
import com.mint.hosttracker.url.domain.Url
import com.mint.hosttracker.url.service.UrlService
import com.mint.hosttracker.url.service.impl.SimpleUrlService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture

@Configuration
class CoreConfiguration {
    @Bean
    fun urlDAO(jdbcTemplate: JdbcTemplate): UrlDAO {
        return SimpleUrlDAO(jdbcTemplate)
    }

    @Bean
    fun idToUrl(urlDAO: UrlDAO): MutableMap<Long, Url> {
        var urlList: HashMap<Long, Url> = HashMap()
        urlList.putAll(urlDAO.getAll().associateBy { it.id!! })

        return urlList
    }

    @Bean
    fun urlService(
        urlDAO: UrlDAO,
        idToUrl: MutableMap<Long, Url>,
        activeUrlTasks: MutableMap<Long, ScheduledFuture<*>>,
        executorService: ScheduledExecutorService,
    ): UrlService {
        return SimpleUrlService(urlDAO, idToUrl, activeUrlTasks, executorService)
    }
}