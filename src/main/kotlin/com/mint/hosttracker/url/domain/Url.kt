package com.mint.hosttracker.url.domain

import java.sql.Timestamp

data class Url(
    val id: Long? = null,
    var value: String,
    var period: Long,
    var status: String? = null,
    var pingTime: Long? = null,
    var createdAt: Timestamp = Timestamp(System.currentTimeMillis()),
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
) {
}