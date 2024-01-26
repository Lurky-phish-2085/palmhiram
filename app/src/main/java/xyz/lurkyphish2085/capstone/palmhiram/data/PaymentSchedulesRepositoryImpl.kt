package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import javax.inject.Inject

class PaymentSchedulesRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PaymentScheduleRepository {

    companion object {
        const val PAYMENT_SCHEDULES_COLLECTIONS_PATH = "payment-schedules"
    }

    override val paymentSchedules: Flow<List<PaymentSchedule>>
        get() = firebaseFirestore.collection(PAYMENT_SCHEDULES_COLLECTIONS_PATH)
            .snapshots().map { snapshots ->
                snapshots.toObjects(PaymentSchedule::class.java)
            }

    override suspend fun addPaymentSchedule(paymentSchedule: PaymentSchedule): Resource<PaymentSchedule> {
        return try {
            val document = firebaseFirestore.collection(PAYMENT_SCHEDULES_COLLECTIONS_PATH)
                .add(paymentSchedule)
                .await()

            val result = document.get().await()

            Resource.Success(result.toObject(PaymentSchedule::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updatePaymentSchedule(
        paymentScheduleId: String,
        updateSchedule: PaymentSchedule
    ): Resource<PaymentSchedule> {
        return try {
            val update = updateSchedule
            update.modified = Timestamp.now()

            val document = firebaseFirestore.collection(PAYMENT_SCHEDULES_COLLECTIONS_PATH)
                .document(paymentScheduleId)
                .set(update)
                .await()

            val result = firebaseFirestore.collection(PAYMENT_SCHEDULES_COLLECTIONS_PATH)
                .document(paymentScheduleId)
                .get()
                .await()

            Resource.Success(result.toObject(PaymentSchedule::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}