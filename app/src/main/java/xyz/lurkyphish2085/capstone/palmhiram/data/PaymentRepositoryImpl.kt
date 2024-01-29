package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.Payment
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PaymentRepository {

    companion object {
        const val PAYMENTS_COLLECTIONS_PATH = "payments"
    }
    override val payments: Flow<List<Payment>>
        get() = firebaseFirestore.collection(PAYMENTS_COLLECTIONS_PATH)
            .snapshots().map {  snapshots ->
                snapshots.toObjects(Payment::class.java)
            }

    override suspend fun addPayment(payment: Payment): Resource<Payment> {
        return try {
            val document = firebaseFirestore.collection(PAYMENTS_COLLECTIONS_PATH)
                .add(payment)
                .await()

            val result = document.get().await()

            Resource.Success(result.toObject(Payment::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getPayment(paymentId: String): Resource<Payment> {
        return try {
            val document = firebaseFirestore.collection(PAYMENTS_COLLECTIONS_PATH)
                .get()
                .await()

            val list = document.toObjects(Payment::class.java)
            val result = list.filter { it.id == paymentId }.get(0)

            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updatePayment(paymentId: String, update: Payment): Resource<Payment> {
        return try {
            val document = firebaseFirestore.collection(PAYMENTS_COLLECTIONS_PATH)
                .document(paymentId)
                .set(update)
                .await()

            val result = firebaseFirestore.collection(PAYMENTS_COLLECTIONS_PATH)
                .document(paymentId)
                .get()
                .await()

            Resource.Success(result.toObject(Payment::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}