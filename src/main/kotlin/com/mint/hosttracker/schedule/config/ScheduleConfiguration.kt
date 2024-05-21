package com.mint.hosttracker.schedule.config

import com.mint.hosttracker.schedule.UrlScheduler
import com.mint.hosttracker.url.domain.Url
import com.mint.hosttracker.url.service.UrlService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture

@Configuration
class ScheduleConfiguration {
    @Bean
    fun executorService(idToUrl: MutableMap<Long, Url>): ScheduledExecutorService {
        return Executors.newScheduledThreadPool(idToUrl.size, Thread.ofVirtual().factory())
    }

    @Bean
    fun urlScheduler(
        idToUrl: MutableMap<Long, Url>,
        urlService: UrlService
    ): UrlScheduler {
        val urlScheduler = UrlScheduler(idToUrl, urlService)
        urlScheduler.startThreads()
        return urlScheduler
    }

    @Bean
    fun activeUrlTasks(): MutableMap<Long, ScheduledFuture<*>> {
        return mutableMapOf()
    }
}