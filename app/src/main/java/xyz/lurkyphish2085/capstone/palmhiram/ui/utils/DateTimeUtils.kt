package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.time.LocalDate
import java.time.Period
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

        fun formatToISO8601Date(date: LocalDate): String {

            return date.format(DateTimeFormatter.ISO_DATE)
        }

        fun calculateYearsBetween(
            date1: LocalDate?,
            date2: LocalDate?
        ): Double {
            // Calculate the period between the two dates
            val period: Period = Period.between(date1, date2)

            // Get the years, months, and days from the period
            val years: Int = period.getYears()
            val months: Int = period.getMonths()
            val days: Int = period.getDays()

            // Convert the period to a decimal representation of years
            return years + months / 12.0 + days / 365.25
        }

        fun parseISO8601DateString(dateString: String?): LocalDate? {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(dateString, formatter)
        }
    }
}