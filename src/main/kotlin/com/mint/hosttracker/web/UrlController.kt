package com.mint.hosttracker.web

import com.mint.hosttracker.url.domain.Url
import com.mint.hosttracker.url.domain.to.CreateUrlTO
import com.mint.hosttracker.url.domain.to.UpdateUrlTO
import com.mint.hosttracker.url.service.UrlService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/url")
class UrlController(var urlService: UrlService) {

    @PostMapping("/create")
    fun create(@RequestBody createUrlTO: CreateUrlTO) {
        val newUrl = Url(value = createUrlTO.url, period = createUrlTO.period)
        urlService.create(newUrl)
    }

    @PostMapping("/update")
    fun update(@RequestBody updateUrlTO: UpdateUrlTO) {
        val urlToUpdate = Url(id = updateUrlTO.id, value = updateUrlTO.url, period = updateUrlTO.period)
        urlService.update(urlToUpdate)
    }

    @DeleteMapping("/delete/{id}")
    fun update(@PathVariable id: Long) {
        urlService.delete(id)
    }
}