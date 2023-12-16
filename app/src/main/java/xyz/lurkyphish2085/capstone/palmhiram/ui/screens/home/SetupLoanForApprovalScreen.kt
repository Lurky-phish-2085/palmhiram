package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CustomTextField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.capitalized
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.replaceUnderscoresWithWhitespaces
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanRepaymentFrequencies
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

@OptIn(ExperimentalStdlibApi::class)
@ExperimentalMaterial3Api
@Composable
fun SetupLoanForApprovalScreen(
    balanceName: String,
    lenderDashboardViewModel: LenderDashboardViewModel,
    viewModel: SetupLoanForApprovalScreenViewModel,
    transactionDetails: LoanTransaction,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var principalAmount by rememberSaveable {
        mutableStateOf("${Money.parseActualValue(transactionDetails.principalAmount)}")
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
    val totalPaymentFlow = viewModel.totalPayment.collectAsState()

    var timeInYears by rememberSaveable {
        mutableStateOf(0.0)
    }

    val calendarStartedOnState = rememberUseCaseState()
    val calendarDueOnState = rememberUseCaseState()

    CalendarDialog(
        state = calendarStartedOnState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date {
            startedOn = DateTimeUtils.formatToISO8601Date(it)
        }
    )
    CalendarDialog(
        state = calendarDueOnState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date { dueDate ->
            dueOn = DateTimeUtils.formatToISO8601Date(dueDate)

            timeInYears = DateTimeUtils.calculateYearsBetween(
                DateTimeUtils.parseISO8601DateString(startedOn),
                dueDate
            )

            // TODO: THIS IS TEMP, please fix this asap
            viewModel.calculateTotalPayment(
                principalAmount = Money(principalAmount.toDouble()),
                interestRatePercent = Integer.valueOf(interestRate),
                timeInYears = timeInYears
            )
        }
    )

    var expandRepaymentDropDown by rememberSaveable {
        mutableStateOf(false)
    }
    var repaymentDropDownSelectedItem by rememberSaveable {
        mutableStateOf(LoanRepaymentFrequencies.values()[0].name.replaceUnderscoresWithWhitespaces().lowercase().capitalized())
    }

    var expandBottomDetails by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Setup Loan",
                onClose = onClose
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
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Text(
                            text = "Total Payment",
                            style = MaterialTheme.typography.titleLarge,
                        )

                        AnimatedVisibility(visible = expandBottomDetails) {
                            Box(modifier = Modifier.weight(1f, true)) {
                                Column(
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    repeat(6) {
                                        Text(text = "Term")
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "₱ ${Money.parseActualValue(totalPaymentFlow.value)}",
                            style = MaterialTheme.typography.titleLarge,
                        )

                        AnimatedVisibility(visible = expandBottomDetails) {
                            Box(modifier = Modifier.weight(1f, true)) {
                                Column(
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    repeat(6) {
                                        Text(text = "₱ 0")
                                    }
                                }
                            }
                        }
                    }
                }



                TextButton(
                    onClick = { expandBottomDetails = !expandBottomDetails },
                ) {
                    Text(
                        text = if (expandBottomDetails) "Show less" else "Show more",
                        style = TextStyle.Default.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        ),
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
                        onValueChange = { principalAmount = it},
                        keyboardType = KeyboardType.Number,
                    )
                    TextFieldWithError(
                        label = "Interest Rate (%)",
                        value = interestRate,
                        passingCondition = { it.isNotBlank() },
                        onValueChange = { interestRate = it },
                        keyboardType = KeyboardType.Number,
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                        ) {
                            CustomTextField(
                                value = startedOn,
                                onValueChange = { /*TODO*/ },
                                label = "Started On",
                                readOnly = true,
                                modifier = Modifier
                                    .width(128.dp)
                            )
                            IconButton(
                                onClick = { calendarStartedOnState.show() },
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
                            CustomTextField(
                                value = dueOn,
                                onValueChange = { /*TODO*/ },
                                label = "Due On",
                                readOnly = true,
                                modifier = Modifier
                                    .width(128.dp)
                            )
                            IconButton(
                                onClick = { calendarDueOnState.show() },
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

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expandRepaymentDropDown,
                                onExpandedChange = { expandRepaymentDropDown = !expandRepaymentDropDown }
                            ) {

                               OutlinedTextField(
                                   value = repaymentDropDownSelectedItem,
                                   label = { Text(text = "Repayment Frequency") },
                                   onValueChange = {},
                                   readOnly = true,
                                   trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(
                                           expanded = expandRepaymentDropDown
                                       )
                                   },
                                   colors = TextFieldDefaults.outlinedTextFieldColors(
                                       focusedBorderColor = MaterialTheme.colorScheme.outline,
                                       focusedLabelColor = MaterialTheme.colorScheme.primary
                                   ),
                                   singleLine = true,
                                   modifier = Modifier.menuAnchor()
                               )

                                DropdownMenu(
                                    expanded = expandRepaymentDropDown,
                                    onDismissRequest = { expandRepaymentDropDown = !expandRepaymentDropDown },
                                    modifier = Modifier.width(258.dp)
                                ) {
                                    LoanRepaymentFrequencies.values().forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item.name.replaceUnderscoresWithWhitespaces().lowercase().capitalized()) },
                                            onClick = {
                                                repaymentDropDownSelectedItem = item.name.replaceUnderscoresWithWhitespaces().lowercase().capitalized()
                                                expandRepaymentDropDown = false
                                            },
                                        )
                                    }
                                }
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
                lenderDashboardViewModel = LenderDashboardViewModel(null, null),
                viewModel = SetupLoanForApprovalScreenViewModel(),
                transactionDetails = LoanTransaction(
                    borrowerName = "Bibong M",
                    principalAmount = 69420L,
                    interestRateInPercentage = 69420,
                    startDate = Timestamp.now(),
                ),
                onClose = {},
                modifier = Modifier
                    .padding(all = 16.dp),
            )
        }
    }
}
