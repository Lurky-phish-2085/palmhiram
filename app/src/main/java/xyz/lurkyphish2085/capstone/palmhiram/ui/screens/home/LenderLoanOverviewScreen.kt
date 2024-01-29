package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NothingToSeeHere
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.PaymentScheduleDateItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus

@Composable
fun LenderLoanOverviewScreen(
    onClose: () -> Unit,
    onSelectedUnderApprovalPaymentItemClick: () -> Unit,
    onSelectedNonUnderApprovalPaymentItemClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: LenderLoanOverviewViewModel,
    modifier: Modifier = Modifier
) {

    val paymentSchedulesDatesFlow = viewModel.paymentSchedulesDatesOfSelectedTransaction.collectAsState()

    fun onSelectItemOnUnderApprovalPaymentItem(item: PaymentScheduleDate) {
        globalState.selectedPaymentDateItem = item
        onSelectedUnderApprovalPaymentItemClick()
    }
    fun onSelectItemOnOtherPendingItem(item: PaymentScheduleDate) {
        viewModel?.getPaymentForSelectedPaymentDate(item)
        globalState.selectedPaymentItem = viewModel?.paymentForSelectedDate!!

        if (globalState.selectedPaymentItem.date != null) {
            onSelectedNonUnderApprovalPaymentItemClick()
        }
    }

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Loan Overview",
                onClose = onClose
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LoanTransactionItemCard(
                onClick = {},
                balanceName = "Total amount to pay",
                transactionDetails = globalState.selectedLoanTransactionItem
            )

            Text(
                text = "Payment Frequency: ${globalState.selectedLoanTransactionItem.repaymentFrequency}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = "Payment Schedules",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Under Approval",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            if (paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.APPROVAL }.isEmpty()) {
                NothingToSeeHere(modifier = Modifier.fillMaxWidth())
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    items(items = paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.APPROVAL }.asReversed(), key = { it.date.toString() }) { scheduleDate ->
                        PaymentScheduleDateItemCard(
                            onClick = { onSelectItemOnUnderApprovalPaymentItem(scheduleDate) },
                            globalState = globalState,
                            details = scheduleDate
                        )
                    }
                }
            }

            Text(
                text = "Pending",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            if (paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.PENDING }.isEmpty()) {
                NothingToSeeHere(modifier = Modifier.fillMaxWidth())
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    items(items = paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.PENDING }.asReversed(), key = { it.date.toString() }) { scheduleDate ->
                        PaymentScheduleDateItemCard(
                            onClick = {},
                            globalState = globalState,
                            details = scheduleDate
                        )
                    }
                }
            }
            Text(
                text = "Approved",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            if (paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.APPROVED }.isEmpty()) {
                NothingToSeeHere(modifier = Modifier.fillMaxWidth())
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    items(items = paymentSchedulesDatesFlow.value.filter { PaymentScheduleDateStatus.valueOf(it.status) == PaymentScheduleDateStatus.APPROVED }.asReversed(), key = { it.date.toString() }) { scheduleDate ->
                        PaymentScheduleDateItemCard(
                            onClick = { onSelectItemOnOtherPendingItem(scheduleDate) },
                            globalState = globalState,
                            details = scheduleDate
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}