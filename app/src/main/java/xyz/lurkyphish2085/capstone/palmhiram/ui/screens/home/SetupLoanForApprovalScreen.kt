package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.widget.Toast
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CancelPresentation
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Cancel
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ConfirmationDialog
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CustomTextField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.capitalized
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.replaceUnderscoresWithWhitespaces
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanRepaymentFrequencies
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

@OptIn(ExperimentalStdlibApi::class)
@ExperimentalMaterial3Api
@Composable
fun SetupLoanForApprovalScreen(
    globalState: FunniGlobalViewModel,
    balanceName: String,
    lenderDashboardViewModel: LenderDashboardViewModel,
    viewModel: SetupLoanForApprovalScreenViewModel,
    transactionDetails: LoanTransaction,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var principalAmount by rememberSaveable {
        mutableStateOf("${Money.parseActualValue(transactionDetails.principalAmount).value}")
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

    var durationInMonths by rememberSaveable {
        mutableStateOf("0")
    }

    var dueOn by rememberSaveable {
        mutableStateOf(
            when(transactionDetails.endDate) {
                transactionDetails.startDate -> "N/A"
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
            viewModel.calculateTotalPaymentAsAnnual(
                principalAmount = Money(principalAmount.toDouble()),
                interestRatePercent = Integer.valueOf(interestRate),
                timeInYears = timeInYears
            )
        }
    )

    var expandRepaymentDropDown by rememberSaveable {
        mutableStateOf(false)
    }
    var repaymentDropDownSelectedItemDisplayText by rememberSaveable {
        mutableStateOf(LoanRepaymentFrequencies.values()[0].name.replaceUnderscoresWithWhitespaces().lowercase().capitalized())
    }

    var expandBottomDetails by rememberSaveable {
        mutableStateOf(false)
    }

    var isDeclineConfirmationDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isSuccessDialogForLoanApproveModalOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isSuccessDialogForLoanDeclineModalOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val paymentScheduleDatesGenerationFlow = viewModel?.paymentScheduleGenerationFlow?.collectAsState()
    paymentScheduleDatesGenerationFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()
                }

                isLoading = false
            }
            Resource.Loading -> { isLoading = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: transaction schedule generated: ${it.result.id}:${it.result.loanTransactionId}", Toast.LENGTH_LONG)
                        .show()

                    isLoading = false
                    viewModel?.approveLoanTransaction(globalState.selectedLoanTransactionItem)
                }
            }

            else -> {}
        }
    }

    val updatedTransactionFlow = viewModel?.updatedLoanTransactionFlow?.collectAsState()
    updatedTransactionFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()
                }

                isLoading = false
            }
            Resource.Loading -> { isLoading = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: transaction status: ${it.result.status}", Toast.LENGTH_LONG)
                        .show()
                }

                isLoading = false

                when(it.result.status) {
                    LoanTransactionStatus.APPROVED.name -> isSuccessDialogForLoanApproveModalOpen = true
                    LoanTransactionStatus.CANCELLED.name -> isSuccessDialogForLoanDeclineModalOpen = true
                }
            }

            else -> {}
        }
    }

    val isPrincipalAmountFieldValid = {
        principalAmount.isNotBlank() &&
        InputValidationUtils.validateNumericWithPoints(principalAmount) &&
        principalAmount.toDouble() > 0.0
    }

    val isInterestRateFieldValid = {
        interestRate.isNotBlank() &&
        InputValidationUtils.validateNumeric(interestRate) &&
        Integer.valueOf(interestRate) > 0 &&
        Integer.valueOf(interestRate) <= 100
    }

    val isDurationInMonthsFieldValid = {
        durationInMonths.isNotBlank() &&
        InputValidationUtils.validateNumeric(durationInMonths) &&
        Integer.valueOf(durationInMonths) > 0
    }

    val isStartOnAndDueDateValid = {
        !startedOn.equals("N/A", true) &&
        !dueOn.equals("N/A", true) &&
        startedOn.isNotBlank() &&
        dueOn.isNotBlank()
    }

    val checkIfAllFieldsOkay = {
            isPrincipalAmountFieldValid() &&
            isInterestRateFieldValid() &&
            isDurationInMonthsFieldValid()
//            && isStartOnAndDueDateValid() // For some reason this crashes lmao!
    }

    var allFieldsValid by rememberSaveable {
        mutableStateOf(false)
    }

    var repaymentFrequencyMode by rememberSaveable {
        mutableStateOf(LoanRepaymentFrequencies.MONTHLY.name)
    }

    fun updateViewModelContents() {
        viewModel?.startDate = startedOn
        viewModel?.dueDate = dueOn
    }
    fun calculateTotalPayment() {
        if (checkIfAllFieldsOkay()) {
            val startedDate = DateTimeUtils.parseISO8601DateString(startedOn)!!
            val dueOnDate = DateTimeUtils.parseISO8601DateString(dueOn)!!

            viewModel.calculateAll(
                Money.valueOf(principalAmount),
                Integer.valueOf(interestRate),
                DateTimeUtils.calculateMonthsBetween(
                    startedDate,
                    dueOnDate,
                ),
                LoanRepaymentFrequencies.valueOf(repaymentFrequencyMode)
            )

            updateViewModelContents()
        } else {
            viewModel.resetMoneyStuffFields()
        }
    }

    fun updateRepaymentFrequencyField(frequency: LoanRepaymentFrequencies) {
        repaymentDropDownSelectedItemDisplayText = frequency.name.replaceUnderscoresWithWhitespaces().lowercase().capitalized()
        repaymentFrequencyMode = frequency.name
    }

    val interestAmountFlow = viewModel.totalInterestAmount.collectAsState()
    val numberOfPaymentsFlow = viewModel.numberOfPayments.collectAsState()
    val amountPerPaymentFlow = viewModel.amountPerPayment.collectAsState()

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Setup Loan",
                onClose = onClose
            ) {
                IconButton(onClick = { isDeclineConfirmationDialogOpen = true }) {
                    Icon(
                        imageVector = Icons.Rounded.Cancel,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
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
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                ) {
                                    Text(text = "Total Interest", textAlign = TextAlign.Left)
                                    Text(text = "Amount per payment", textAlign = TextAlign.Left)
                                    Text(text = "Number of payments", textAlign = TextAlign.Left)
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
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                ) {
                                    Text(text = "₱ ${Money.parseActualValue(interestAmountFlow.value)}", textAlign = TextAlign.Right)
                                    Text(text = "₱ ${Money.parseActualValue(amountPerPaymentFlow.value)}", textAlign = TextAlign.Right)
                                    Text(text = "${numberOfPaymentsFlow.value}", textAlign = TextAlign.Right)
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
                    onclick = {
                        viewModel?.generatePaymentScheduleForApprovedLoan(globalState.selectedLoanTransactionItem)
                    },
                    enabled = allFieldsValid
                )
            }
        },
        modifier = modifier
    ) { padding ->
        CircularProgressLoadingIndicator(isOpen = isLoading)
        ConfirmationDialog(
            isOpen = isDeclineConfirmationDialogOpen,
            onPositiveClick = {
                viewModel?.declineLoanTransaction(globalState.selectedLoanTransactionItem.id)
                isDeclineConfirmationDialogOpen = false
            },
            onNegativeClick = { isDeclineConfirmationDialogOpen = false },
            onDismissRequest = { isDeclineConfirmationDialogOpen = false },
            onClose = { isDeclineConfirmationDialogOpen = false },
            title = "Warning",
            icon = Icons.Default.CancelPresentation,
            headline = "Decline Requested Loan?",
            description = "This action cannot be undone.",
            positiveButtonText = "YES",
            negativeButtonText = "NO",
        )
        ConfirmationDialog(
            isOpen = isSuccessDialogForLoanApproveModalOpen,
            positiveButtonOnly = true,
            onPositiveClick = {
                isSuccessDialogForLoanApproveModalOpen = false
                onClose()
            },
            onNegativeClick = {
                isSuccessDialogForLoanApproveModalOpen = false
                onClose()
            },
            onDismissRequest = {
                isSuccessDialogForLoanApproveModalOpen = false
                onClose()
            },
            onClose = {
                isSuccessDialogForLoanApproveModalOpen = false
                onClose()
            },
            title = "Success",
            icon = Icons.Default.Done,
            headline = "Loan Approved",
            description = "The loan has been successfully approved and is ongoing.",
            positiveButtonText = "OKAY",
            negativeButtonText = ""
        )
        ConfirmationDialog(
            isOpen = isSuccessDialogForLoanDeclineModalOpen,
            positiveButtonOnly = true,
            onPositiveClick = {
                isSuccessDialogForLoanDeclineModalOpen = false
                onClose()
            },
            onNegativeClick = {
                isSuccessDialogForLoanDeclineModalOpen = false
                onClose()
            },
            onDismissRequest = {
                isSuccessDialogForLoanDeclineModalOpen = false
                onClose()
            },
            onClose = {
                isSuccessDialogForLoanDeclineModalOpen = false
                onClose()
            },
            title = "Success",
            icon = Icons.Default.Done,
            headline = "Loan Cancelled",
            description = "The loan has been successfully cancelled.",
            positiveButtonText = "OKAY",
            negativeButtonText = ""
        )

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
                        passingCondition = {
                            it.isNotBlank() &&
                            InputValidationUtils.validateNumericWithPoints(it) &&
                            it.toDouble() > 0.0
                        },
                        onValueChange = {
                            principalAmount = it
                            allFieldsValid = checkIfAllFieldsOkay()
                            calculateTotalPayment()
                        },
                        keyboardType = KeyboardType.Number,
                    )
                    TextFieldWithError(
                        label = "Interest Rate (%)",
                        value = interestRate,
                        passingCondition = {
                           it.isNotBlank() &&
                           InputValidationUtils.validateNumeric(it) &&
                           Integer.valueOf(it) > 0 &&
                           Integer.valueOf(it) <= 100
                        },
                        onValueChange = {
                            interestRate = it
                            allFieldsValid = checkIfAllFieldsOkay()
                            calculateTotalPayment()
                        },
                        keyboardType = KeyboardType.Number,
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandRepaymentDropDown,
                        onExpandedChange = { expandRepaymentDropDown = !expandRepaymentDropDown }
                    ) {

                        OutlinedTextField(
                            value = repaymentDropDownSelectedItemDisplayText,
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
                            LoanRepaymentFrequencies.values().forEach { frequencyItem ->
                                DropdownMenuItem(
                                    text = { Text(text = frequencyItem.name.replaceUnderscoresWithWhitespaces().lowercase().capitalized()) },
                                    onClick = {
                                        updateRepaymentFrequencyField(frequencyItem)
                                        expandRepaymentDropDown = false
                                        calculateTotalPayment()
                                    },
                                )
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp)
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
                                .padding(horizontal = 25.dp)
                        ) {
                            TextFieldWithError(
                                /*TODO*/
                                label = "Duration in months",
                                value = durationInMonths,
                                passingCondition = {
                                    it.isNotBlank() &&
                                    InputValidationUtils.validateNumeric(it) &&
                                    Integer.valueOf(it) > 0
                                },
                                onValueChange = {
                                    durationInMonths = it
                                    allFieldsValid = checkIfAllFieldsOkay()

                                    // Don't use the calculateTotalPayment function here, it crashes for some reason
                                    if (allFieldsValid) {
                                        val startedDate = DateTimeUtils.convertToLocalDateToDate(DateTimeUtils.parseISO8601DateString(startedOn)!!)!!
                                        val dueOnDate = DateTimeUtils.addMonthsToDate(startedDate, Integer.valueOf(it) )

                                        dueOn = DateTimeUtils.formatToISO8601Date(dueOnDate)

                                        viewModel.calculateAll(
                                            Money.valueOf(principalAmount),
                                            Integer.valueOf(interestRate),
                                            DateTimeUtils.calculateMonthsBetween(
                                                DateTimeUtils.convertToDateToLocalDate(startedDate)!!,
                                                DateTimeUtils.convertToDateToLocalDate(dueOnDate)!!,
                                            ),
                                            LoanRepaymentFrequencies.valueOf(repaymentFrequencyMode)
                                        )

                                        updateViewModelContents()
                                    } else {
                                        dueOn = "N/A"
                                        viewModel.resetMoneyStuffFields()
                                    }
                                },
                                keyboardType = KeyboardType.NumberPassword,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp)
                        ) {
                            CustomTextField(
                                value = dueOn,
                                onValueChange = { /*TODO*/ },
                                label = "Due On",
                                readOnly = true,
                                modifier = Modifier
                                    .width(128.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Remarks:", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        BasicText(text = "${transactionDetails.remarks}", maxLines = 12)
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
                globalState = FunniGlobalViewModel(),
                balanceName = "Total amount to collect",
                lenderDashboardViewModel = LenderDashboardViewModel(null, null, null),
                viewModel = SetupLoanForApprovalScreenViewModel(null, null),
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
