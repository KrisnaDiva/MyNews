package com.krisna.diva.mynews.core.utils

import org.junit.Assert.*
import org.junit.Test
import java.time.format.DateTimeParseException
import java.time.zone.ZoneRulesException

class DateUtilsTest {
    @Test
    fun `given correct ISO 8601 format then should format correctly`() {
        val currentDate = "2022-02-02T10:10:10Z"
        assertEquals("02 Feb 2022 | 17:10", DateUtils.formatDate(currentDate, "Asia/Jakarta"))
        assertEquals("02 Feb 2022 | 18:10", DateUtils.formatDate(currentDate, "Asia/Makassar"))
        assertEquals("02 Feb 2022 | 19:10", DateUtils.formatDate(currentDate, "Asia/Jayapura"))
    }

    @Test
    fun `given wrong ISO 8601 format then should throw error`() {
        val wrongFormat = "2022-02-02T10:10"
        assertThrows(DateTimeParseException::class.java) {
            DateUtils.formatDate(wrongFormat, "Asia/Jakarta")
        }
    }
    @Test
    fun `given invalid timezone then should throw error`() {
        val wrongFormat = "2022-02-02T10:10:10Z"
        assertThrows(ZoneRulesException::class.java) {
            DateUtils.formatDate(wrongFormat, "Asia/Bandung")
        }
    }
}