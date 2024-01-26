package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme


@Composable
fun BorrowerLoanOverviewScreen(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerLoanOverviewViewModel,
    modifier: Modifier = Modifier
) {
    val paymentSchedulesDatesFlow = viewModel.paymentSchedulesDatesOfSelectedTransaction.collectAsState()

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
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LoanTransactionItemCard(
                onClick = {},
                balanceName = "Total amount to pay",
                transactionDetails = globalState.selectedLoanTransactionItem
            )

            Text(
                text = "Payment Schedules",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Pending",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(512.dp)
            ) {
                items(items = paymentSchedulesDatesFlow.value.filter { it.settled == false }, key = { it.date.toString() }) { scheduleDate ->
                    PaymentScheduleDateItemCard(
                        onClick = { /*TODO*/ },
                        globalState = globalState,
                        details = scheduleDate
                    )
                }
            }
            Text(
                text = "Settled",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(512.dp)
            ) {
                items(items = paymentSchedulesDatesFlow.value.filter { it.settled == true }, key = { it.date.toString() }) { scheduleDate ->
                    PaymentScheduleDateItemCard(
                        onClick = { /*TODO*/ },
                        globalState = globalState,
                        details = scheduleDate
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PaymentScheduleDateItemCard(
    onClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    details: PaymentScheduleDate,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${details.date}", modifier = Modifier.align(Alignment.CenterStart))
            Text(text = "${globalState.selectedLoanTransactionItem.paymentPerSchedule}", modifier = Modifier.align(Alignment.CenterStart))
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun BorrowerPaymentScreenPreview() {
    PalmHiramTheme {
        Surface {
            BorrowerLoanOverviewScreen(
                onClose = {},
                globalState = FunniGlobalViewModel(),
                viewModel = BorrowerLoanOverviewViewModel(null, null),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}