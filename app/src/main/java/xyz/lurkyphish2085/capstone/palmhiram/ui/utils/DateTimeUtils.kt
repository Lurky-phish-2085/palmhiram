package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DateTimeUtils {

    companion object {

        fun formatToISO8601Date(date: Date): String {
            val instant = date.toInstant()
            val zoneId = ZoneId.systemDefault()
            val zonedDateTime = instant.atZone(zoneId)

            return zonedDateTime.toLocalDateTime().format(DateTimeFormatter.ISO_DATE)
        }
    }
}