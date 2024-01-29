package xyz.lurkyphish2085.capstone.palmhiram.data

import kotlinx.coroutines.flow.Flow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.Payment
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule

interface PaymentRepository {

    val payments: Flow<List<Payment>>

    suspend fun addPayment(payment: Payment): Resource<Payment>
    suspend fun getPayment(paymentId: String): Resource<Payment>
    suspend fun updatePayment(paymentId: String, update: Payment): Resource<Payment>
}