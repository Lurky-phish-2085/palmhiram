package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import javax.inject.Inject

class LoanTransactionRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : LoanTransactionRepository {

    companion object {
        const val LOAN_TRANSACTIONS_COLLECTIONS_PATH = "loan-transactions"
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