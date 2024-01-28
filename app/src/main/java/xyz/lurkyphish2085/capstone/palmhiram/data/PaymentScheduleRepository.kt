package xyz.lurkyphish2085.capstone.palmhiram.data

import kotlinx.coroutines.flow.Flow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate

interface PaymentScheduleRepository {

    val paymentSchedules: Flow<List<PaymentSchedule>>
    val paymentDates: Flow<List<PaymentScheduleDate>>

    suspend fun addPaymentSchedule(paymentSchedule: PaymentSchedule): Resource<PaymentSchedule>
    suspend fun updatePaymentSchedule(paymentScheduleId: String, updateSchedule: PaymentSchedule): Resource<PaymentSchedule>
    suspend fun addPaymentDate(paymentDate: PaymentScheduleDate): Resource<PaymentScheduleDate>
    suspend fun addPaymentDates(paymentDates: List<PaymentScheduleDate>, loanTransactionId: String): Resource<List<PaymentScheduleDate>>
    suspend fun updatePaymentDate(paymentScheduleDateId: String, update: PaymentScheduleDate): Resource<PaymentScheduleDate>
}