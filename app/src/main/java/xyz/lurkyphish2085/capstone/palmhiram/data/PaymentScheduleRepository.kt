package xyz.lurkyphish2085.capstone.palmhiram.data

import kotlinx.coroutines.flow.Flow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule

interface PaymentScheduleRepository {

    val transactions: Flow<List<PaymentSchedule>>

    suspend fun addPaymentSchedule(paymentSchedule: PaymentSchedule): Resource<PaymentSchedule>
    suspend fun updatePaymentSchedule(paymentScheduleId: String, updateSchedule: PaymentSchedule): Resource<PaymentSchedule>
}