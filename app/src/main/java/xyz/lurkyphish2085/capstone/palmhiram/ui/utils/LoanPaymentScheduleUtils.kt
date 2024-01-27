package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import android.util.Log
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanRepaymentFrequencies
import java.util.Date

class LoanPaymentScheduleUtils {

    companion object {
        const val DAYS_PER_MONTH = 30
        const val DAYS_PER_SEMI_MONTH = 15
        const val DAYS_PER_WEEK = 7

        fun generateDateSchedules(
            startDate: Date,
            paymentFrequency: LoanRepaymentFrequencies,
            numberOfPayments: Int,
        ): List<Date> {
            val paymentScheduleDates = arrayListOf<Date>()
            val daysToAdd =
                when(paymentFrequency) {
                    LoanRepaymentFrequencies.MONTHLY -> DAYS_PER_MONTH
                    LoanRepaymentFrequencies.SEMI_MONTHLY -> DAYS_PER_SEMI_MONTH
                    LoanRepaymentFrequencies.WEEKLY -> DAYS_PER_WEEK
                }

            repeat(numberOfPayments) {
                if (paymentScheduleDates.isEmpty()) {
                    val newDate = DateTimeUtils.setDateTimeToMidnight(DateTimeUtils.addDaysToDate(startDate, daysToAdd))
                    paymentScheduleDates.add(newDate)
                    Log.e("PAYMENT GEN", "${newDate.toString()}")
                } else {
                    val lastDate = paymentScheduleDates.last()
                    val newDate = DateTimeUtils.setDateTimeToMidnight(DateTimeUtils.addDaysToDate(lastDate, daysToAdd))
                    paymentScheduleDates.add(newDate)
                    Log.e("PAYMENT GEN", "${newDate.toString()}")
                }
            }

            return paymentScheduleDates
        }
    }
}