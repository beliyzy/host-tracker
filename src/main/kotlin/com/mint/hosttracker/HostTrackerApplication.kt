package com.mint.hosttracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HostTrackerApplication

fun main(args: Array<String>) {
    runApplication<HostTrackerApplication>(*args)
}
