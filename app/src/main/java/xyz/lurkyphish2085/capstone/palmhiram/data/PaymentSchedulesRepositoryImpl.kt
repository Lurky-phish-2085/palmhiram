package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import javax.inject.Inject

class PaymentSchedulesRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PaymentScheduleRepository {

    companion object {
        const val PAYMENT_SCHEDULES_COLLECTIONS_PATH = "payment-schedules"
        const val PAYMENT_DATES_PATH = "payment-dates"
    }

    override val paymentSchedules: Flow<List<PaymentSchedule>>
        get() = firebaseFirestore.collection(PAYMENT_SCHEDULES_COLLECTIONS_PATH)
            .snapshots().map { snapshots ->
                snapshots.toObjects(PaymentSchedule::class.java)
            }
    override val paymentDates: Flow<List<PaymentScheduleDate>>
        get() = firebaseFirestore.collection(PAYMENT_DATES_PATH)
            .snapshots().map { snapshots ->
                snapshots.toObjects(PaymentScheduleDate::class.java)
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

    override suspend fun addPaymentDate(paymentDate: PaymentScheduleDate): Resource<PaymentScheduleDate> {
        return try {
            val document = firebaseFirestore.collection(PAYMENT_DATES_PATH)
                .add(paymentDate)
                .await()

            val result = document.get().await()

            Resource.Success(result.toObject(PaymentScheduleDate::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addPaymentDates(paymentDates: List<PaymentScheduleDate>, loanTransactionId: String): Resource<List<PaymentScheduleDate>> {
        return try {
            val batch: WriteBatch = firebaseFirestore.batch()

            paymentDates.forEach { doc ->
                val docRef = firebaseFirestore.collection(PAYMENT_DATES_PATH).document()
                batch.set(docRef, doc)
            }

            batch.commit().await()

            val collectionContents =
                firebaseFirestore
                    .collection(PAYMENT_DATES_PATH)
                    .get()
                    .await()

            val result = collectionContents
                .toObjects(PaymentScheduleDate::class.java)
                .filter {
                    it.loanTransactionId == loanTransactionId
                }

            return Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updatePaymentDate(paymentScheduleDateId: String, update: PaymentScheduleDate): Resource<PaymentScheduleDate> {
        return try {
            val document = firebaseFirestore.collection(PAYMENT_DATES_PATH)
                .document(paymentScheduleDateId)
                .set(update)
                .await()

            val result = firebaseFirestore.collection(PAYMENT_DATES_PATH)
                .document(paymentScheduleDateId)
                .get()
                .await()

            Resource.Success(result.toObject(PaymentScheduleDate::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}