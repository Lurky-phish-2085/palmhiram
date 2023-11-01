package xyz.lurkyphish2085.capstone.palmhiram.data

import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction

interface LoanTransactionRepository {

    suspend fun getLoanTransaction(transactionId: String): Resource<LoanTransaction>
    suspend fun getLoanTransactionsByUser(userId: String): Resource<List<LoanTransaction>>
    suspend fun addLoanTransaction(loanTransaction: LoanTransaction): Resource<LoanTransaction>
    suspend fun updateLoanTransaction(loanTransactionId: String, loanTransactionUpdate: LoanTransaction): Resource<LoanTransaction>
}