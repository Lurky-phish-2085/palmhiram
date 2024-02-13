package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NothingToSeeHere
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BorrowerLoanProfileScreen(
    onClose: () -> Unit,
    onItemClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerLoanProfileViewModel,
    modifier: Modifier = Modifier
) {
    val totalPayment = viewModel.totalAmountPayment.collectAsState()
    val borrowerUser = viewModel.borrowerUser.collectAsState()
    val pendingLoans = viewModel.borrowerPendingLoan.collectAsState()
    val settledLoans = viewModel.borrowerSettledLoans.collectAsState()
    val cancelledLoans = viewModel.borrowerCancelledLoans.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopBarWithBackButton(
            text = "Borrower Loan Profile",
            onClose = onClose,
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Borrower Name: ${borrowerUser.value?.name}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Total amount to collect: ${totalPayment.value}",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Pending Loan",
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
        if (pendingLoans.value.isNullOrEmpty()) {
            NothingToSeeHere()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.size(512.dp)
            ) {
                items(pendingLoans.value!!, key = { it.id }) { loan ->
                    LoanTransactionItemCard(
                        onClick = {
                            globalState.selectedLoanTransactionItem = loan
                        },
                        balanceName = "Total amount to collect",
                        transactionDetails = loan,
                        modifier = Modifier
                            .animateItemPlacement()
                    )
                }
            }
        }

        Text(
            text = "Settled Loans",
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
        if (settledLoans.value.isNullOrEmpty()) {
            NothingToSeeHere()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.size(512.dp)
            ) {
                items(settledLoans.value!!, key = { it.id }) { loan ->
                    LoanTransactionItemCard(
                        onClick = {
                            globalState.selectedLoanTransactionItem = loan
                            onItemClick()
                        },
                        balanceName = "Total amount to collect",
                        transactionDetails = loan,
                        modifier = Modifier
                            .animateItemPlacement()
                    )
                }
            }
        }

        Text(
            text = "Cancelled Loans",
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
        if (cancelledLoans.value.isNullOrEmpty()) {
            NothingToSeeHere()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.size(512.dp)
            ) {
                items(cancelledLoans.value!!, key = { it.id }) { loan ->
                    LoanTransactionItemCard(
                        onClick = {
                            globalState.selectedLoanTransactionItem = loan
                            onItemClick()
                        },
                        balanceName = "Total amount to collect",
                        transactionDetails = loan,
                        modifier = Modifier
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}