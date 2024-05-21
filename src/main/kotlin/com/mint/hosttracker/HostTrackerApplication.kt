package com.mint.hosttracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
class HostTrackerApplication

fun main(args: Array<String>) {
    runApplication<HostTrackerApplication>(*args)
}
