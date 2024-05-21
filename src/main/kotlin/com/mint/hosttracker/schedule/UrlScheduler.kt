package com.mint.hosttracker.schedule

import com.mint.hosttracker.url.domain.Url
import com.mint.hosttracker.url.service.UrlService

class UrlScheduler(
    private val idToUrl: MutableMap<Long, Url>,
    private val urlService: UrlService
) {
    fun startThreads() {
        idToUrl.forEach { (id, url) ->
            urlService.scheduleTask(id, url)
        }
    }
}

