package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import java.time.DateTimeException

@ExperimentalMaterial3Api
@Composable
fun SetupLoanForApprovalScreen(
    balanceName: String,
    transactionDetails: LoanTransaction,
    modifier: Modifier = Modifier
) {
    var principalAmount by rememberSaveable {
        mutableStateOf("${transactionDetails.principalAmount}")
    }
    var interestRate by rememberSaveable {
        mutableStateOf("${transactionDetails.interestRateInPercentage}")
    }
    var startedOn by rememberSaveable {
        mutableStateOf(
            when(transactionDetails.startDate) {
                null -> "N/A"
                else -> DateTimeUtils.formatToISO8601Date(transactionDetails.startDate?.toDate()!!)
            }
        )
    }
    var dueOn by rememberSaveable {
        mutableStateOf(
            when(transactionDetails.endDate) {
                null -> "N/A"
                else -> DateTimeUtils.formatToISO8601Date(transactionDetails.endDate?.toDate()!!)
            }
        )
    }
    var totalPayment by rememberSaveable {
        mutableStateOf(Money.parseActualValue(transactionDetails.totalPayment).toString())
    }

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Setup Loan",
                onClose = { /*TODO*/ }
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total Payment",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    )
                    Text(
                        text = "₱ $totalPayment",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                WideButton(
                    text = "SUBMIT",
                    onclick = { /*TODO*/ }
                )
            }
        },
        modifier = modifier
    ) { padding ->
        Box(Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            ) {
                LoanTransactionItemCard(
                    balanceName = balanceName,
                    transactionDetails = transactionDetails
                )

                Divider(Modifier.padding(top = 16.dp, bottom = 2.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    TextFieldWithError(
                        label = "Principal Amount",
                        value = principalAmount,
                        passingCondition = { it.isNotBlank() },
                        onValueChange = { principalAmount = it },
                        keyboardType = KeyboardType.Number,
                    )
                    TextFieldWithError(
                        label = "Interest Rate (%)",
                        value = interestRate,
                        passingCondition = { it.isNotBlank() },
                        onValueChange = { interestRate = "$it" },
                        keyboardType = KeyboardType.Number,
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                        ) {
                            TextFieldWithError(
                                label = "Started On",
                                value = startedOn,
                                passingCondition = { it.isNotBlank() },
                                onValueChange = { /*TODO*/ },
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier
                                    .width(128.dp)
                            )

                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                        ) {
                            TextFieldWithError(
                                label = "Due On",
                                value = dueOn,
                                passingCondition = { it.isNotBlank() },
                                onValueChange = { /*TODO*/ },
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier
                                    .width(128.dp)
                            )

                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun SetupLoanForApprovalScreenPreview() {
    PalmHiramTheme {
        Surface {
            SetupLoanForApprovalScreen(
                balanceName = "Total amount to collect",
                transactionDetails = LoanTransaction(
                    borrowerName = "Mal Boros",
                    status = LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER.toString(),
                    principalAmount = 69240,
                    totalBalance = 69420,
                    totalPayment = 0,
                    startDate = Timestamp.now(),
                    endDate = null,
                ),
                modifier = Modifier
                    .padding(all = 16.dp),
            )
        }
    }
}
