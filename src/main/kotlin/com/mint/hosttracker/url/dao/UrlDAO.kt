package com.mint.hosttracker.url.dao

import com.mint.hosttracker.url.domain.Url

interface UrlDAO {
    fun create(url: Url): Url?;

    fun update(url: Url): Url?;

    fun delete(id: Long): Long?;

    fun getById(id: Long): Url?;

    fun getAll(): List<Url>;
}