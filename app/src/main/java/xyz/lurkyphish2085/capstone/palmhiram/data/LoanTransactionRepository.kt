package xyz.lurkyphish2085.capstone.palmhiram.data

import kotlinx.coroutines.flow.Flow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

interface LoanTransactionRepository {

    val transactions: Flow<List<LoanTransaction>>
    val transactionsOrderedByEndDateAsc: Flow<List<LoanTransaction>>
    val transactionsOrderedByStartDateDesc: Flow<List<LoanTransaction>>

    suspend fun getLoanTransaction(transactionId: String): Resource<LoanTransaction>
    suspend fun getLoanTransactionsByUser(userId: String): Resource<List<LoanTransaction>>
    suspend fun addLoanTransaction(loanTransaction: LoanTransaction): Resource<LoanTransaction>
    suspend fun updateLoanTransaction(loanTransactionId: String, loanTransactionUpdate: LoanTransaction): Resource<LoanTransaction>
    suspend fun declineLoanTransaction(loanTransactionId: String): Resource<LoanTransaction>
}