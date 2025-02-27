package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date


class DateTimeUtils {

    companion object {

        fun formatToISO8601Date(date: Date): String {
            val instant = date.toInstant()
            val zoneId = ZoneId.systemDefault()
            val zonedDateTime = instant.atZone(zoneId)

            return zonedDateTime.toLocalDateTime().format(DateTimeFormatter.ISO_DATE)
        }

        fun formatToISO8601NullDate(date: Date?): String? {
            val instant = date?.toInstant()
            val zoneId = ZoneId.systemDefault()
            val zonedDateTime = instant?.atZone(zoneId)

            return zonedDateTime?.toLocalDateTime()?.format(DateTimeFormatter.ISO_DATE)
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

        fun calculateMonthsBetween(startDate: LocalDate, endDate: LocalDate): Int {
            // Ensure both dates are set to the first day of the month
            var startDate = startDate
            var endDate = endDate
            startDate = startDate.withDayOfMonth(1)
            endDate = endDate.withDayOfMonth(1)

            // Calculate months between two LocalDates
            return ChronoUnit.MONTHS.between(startDate, endDate).toInt()
        }

        fun parseISO8601DateString(dateString: String?): LocalDate? {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(dateString, formatter)
        }

        fun addMonthsToDate(date: Date, monthsToAdd: Int): Date {
            var resultDate = date

            repeat(monthsToAdd) {
                resultDate = addDaysToDate(resultDate, 30)
            }

            return resultDate
        }

        fun addDaysToDate(date: Date, daysToAdd: Int): Date {
            // Create a Date object representing the current date
            val currentDate = date

            // Convert the Date to Calendar
            val calendar = Calendar.getInstance()
            calendar.time = currentDate

            // Add days to the current date
            calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)

            // Get the updated Date
            val newDate = calendar.time

            return newDate
        }

        fun convertToLocalDateToDate(localDate: LocalDate): Date? {
            // Convert LocalDate to Instant
            // Set the time to midnight (start of the day)
            // Convert Instant to Date
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        }

        fun convertToDateToLocalDate(date: Date): LocalDate? {
            val instant = date.toInstant()
            return instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun convertLocalDateToTimestamp(localDate: LocalDate?): Timestamp? {
            // Combine LocalDate with a specific time (e.g., midnight)
            val date = convertToLocalDateToDate(localDate!!)

            // Convert LocalDateTime to Timestamp
            return Timestamp(date!!)
        }

        fun setDateTimeToMidnight(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            // Get the modified Date object
            val modifiedDate = calendar.time

            return modifiedDate
        }
    }
}