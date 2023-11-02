package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

class LoanTransactionRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : LoanTransactionRepository {

    companion object {
        const val LOAN_TRANSACTIONS_COLLECTIONS_PATH = "loan-transactions"
        const val ORDER_BY_END_DATE = "endDate"
    }

    override val transactions: Flow<List<LoanTransaction>>
        get() = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
            .orderBy(ORDER_BY_END_DATE, Query.Direction.DESCENDING)
            .snapshots().map { snapshots ->
                snapshots.toObjects(LoanTransaction::class.java)
            }

    override suspend fun getLoanTransaction(transactionId: String): Resource<LoanTransaction> {
        return try {
            val document = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
                .whereEqualTo("id", transactionId)
                .get()
                .await()

            val result = document.documents.get(0)

            Resource.Success(result.toObject(LoanTransaction::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getLoanTransactionsByUser(userId: String): Resource<List<LoanTransaction>> {
        return try {
            val result = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
                .whereEqualTo("borrowerId", userId)
                .get()
                .await()

            Resource.Success(result.toObjects(LoanTransaction::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addLoanTransaction(loanTransaction: LoanTransaction): Resource<LoanTransaction> {
        return try {
            val document = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
                .add(loanTransaction)
                .await()

            val result = document.get().await()

            Resource.Success(result.toObject(LoanTransaction::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateLoanTransaction(loanTransactionId: String, loanTransactionUpdate: LoanTransaction): Resource<LoanTransaction> {
        return try {
            val document = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
                .document(loanTransactionId)
                .set(loanTransactionUpdate)
                .await()

            val result = firebaseFirestore.collection(LOAN_TRANSACTIONS_COLLECTIONS_PATH)
                .document(loanTransactionId)
                .get()
                .await()

            Resource.Success(result.toObject(LoanTransaction::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}