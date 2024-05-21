package com.mint.hosttracker.url.dao.impl

import com.mint.hosttracker.url.dao.UrlDAO
import com.mint.hosttracker.url.domain.Url
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class SimpleUrlDAO(private var jdbcTemplate: JdbcTemplate) : UrlDAO {

    private var rowMapper: RowMapper<Url> = UrlRowMapper()

    override fun create(url: Url): Url? {
        return jdbcTemplate.queryForObject(
            "INSERT INTO url (value, period, created_at, updated_at) VALUES (?, ?, ?, ?) RETURNING *", rowMapper,
            url.value, url.period, url.createdAt, url.updatedAt
        )
    }

    override fun update(url: Url): Url? {
        return try {
            jdbcTemplate.queryForObject(
                "UPDATE url SET value = ?, period = ?, ping_time = ?, status = ?, updated_at = ? WHERE id = ? RETURNING *",
                rowMapper,
                url.value,
                url.period,
                url.pingTime,
                url.status,
                url.updatedAt,
                url.id
            )
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    override fun delete(id: Long): Long? {
        return jdbcTemplate.queryForObject("DELETE FROM url WHERE id = ? RETURNING ID", { rs, _ -> rs.getLong(1) }, id)
            ?.toLong()
    }

    override fun getById(id: Long): Url? {
        return try {
            jdbc().queryForObject("SELECT * FROM url WHERE id = ?", rowMapper, id)
        } catch (emptyEx: EmptyResultDataAccessException) {
            null
        }
    }

    override fun getAll(): List<Url> {
        return jdbcTemplate.query("SELECT * FROM url", rowMapper)

    }

    private fun jdbc(): JdbcTemplate {
        return jdbcTemplate
    }

    private class UrlRowMapper : RowMapper<Url> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Url {
            return Url(
                rs.getLong("id"),
                rs.getString("value"),
                rs.getLong("period"),
                rs.getString("status"),
                rs.getLong("ping_time"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
            )
        }
    }
}