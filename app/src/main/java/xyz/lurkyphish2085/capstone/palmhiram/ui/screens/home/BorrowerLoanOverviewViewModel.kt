package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentScheduleRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import javax.inject.Inject


class BorrowerLoanOverviewViewModel @Inject constructor(
    private val loanTransactionRepository: LoanTransactionRepository?,
    private val paymentScheduleRepository: PaymentScheduleRepository?,
): ViewModel() {

    val paymentSchedules: Flow<List<PaymentSchedule>>
        get() = paymentScheduleRepository?.paymentSchedules!!

    private var _paymentSchedulesOfSelectedTransaction = MutableStateFlow<List<PaymentSchedule>>(
        listOf(
            PaymentSchedule()
        )
    )
    val paymentSchedulesOfSelectedTransaction: StateFlow<List<PaymentSchedule>> =
        _paymentSchedulesOfSelectedTransaction

    private var _paymentSchedulesDatesOfSelectedTransaction =
        MutableStateFlow<List<PaymentScheduleDate>>(listOf(PaymentScheduleDate()))
    val paymentSchedulesDatesOfSelectedTransaction: StateFlow<List<PaymentScheduleDate>> =
        _paymentSchedulesDatesOfSelectedTransaction

    fun collectPaymentScheduleOfLoan(selectedLoanTransactionId: String) = viewModelScope.launch {
        paymentSchedules
            .collectLatest { schedules ->
                val collectedSchedules = schedules.filter { schedule ->
                    schedule.loanTransactionId == selectedLoanTransactionId
                }

                _paymentSchedulesOfSelectedTransaction.value = collectedSchedules
                collectPaymentScheduleDatesOfLoan()
            }
    }

    private fun collectPaymentScheduleDatesOfLoan() = viewModelScope.launch {
        paymentSchedulesOfSelectedTransaction.value.forEach {
            _paymentSchedulesDatesOfSelectedTransaction.value = it.paymentDates
        }
    }
}