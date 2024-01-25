package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanRepaymentFrequencies
import java.util.Date

class LoanPaymentScheduleUtils {

    companion object {
        const val DAYS_PER_MONTH = 30
        const val DAYS_PER_SEMI_MONTH = 15
        const val DAYS_PER_WEEK = 7

        fun generateDateSchedules(
            startDate: Date,
            dueDate: Date,
            paymentFrequency: LoanRepaymentFrequencies
        ): List<Date> {

            val paymentScheduleDates = arrayListOf<Date>()
            val daysToAdd =
                when(paymentFrequency) {
                    LoanRepaymentFrequencies.MONTHLY -> DAYS_PER_MONTH
                    LoanRepaymentFrequencies.SEMI_MONTHLY -> DAYS_PER_SEMI_MONTH
                    LoanRepaymentFrequencies.WEEKLY -> DAYS_PER_WEEK
                }

            while(paymentScheduleDates.lastOrNull() != dueDate) {
                if (paymentScheduleDates.isEmpty()) {
                    paymentScheduleDates.add(DateTimeUtils.addDaysToDate(startDate, daysToAdd))
                    continue
                }

                val lastDate = paymentScheduleDates.last()
                paymentScheduleDates.add(DateTimeUtils.addDaysToDate(lastDate, daysToAdd))
            }

            return paymentScheduleDates
        }
    }
}