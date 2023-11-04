package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.StateFlow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopNavigationTab
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles

@ExperimentalMaterial3Api
@Composable
fun LoansScreen(
    role: UserRoles,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val balanceName: String =
        when(role) {
            UserRoles.BORROWER -> "TOTAL AMOUNT TO PAY"
            UserRoles.LENDER -> "TOTAL AMOUNT TO COLLECT"
        }
    val approvalLoanTransactionFlow: State<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.approvalLoanTransactions?.collectAsState()!!
            UserRoles.LENDER -> lenderDashboardViewModel?.approvalLoanTransactions?.collectAsState()!!
        }
    val ongoingLoanTransactionFlow: State<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.ongoingLoanTransactions?.collectAsState()!!
            UserRoles.LENDER -> lenderDashboardViewModel?.ongoingLoanTransactions?.collectAsState()!!
        }
    val settledLoanTransactionFlow: State<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.settledLoanTransactions?.collectAsState()!!
            UserRoles.LENDER -> lenderDashboardViewModel?.settledLoanTransactions?.collectAsState()!!
        }
    val cancelledLoanTransactionFlow: State<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.cancelledLoanTransactions?.collectAsState()!!
            UserRoles.LENDER -> lenderDashboardViewModel?.cancelledLoanTransactions?.collectAsState()!!
        }

    var selectedTab by rememberSaveable {
        mutableStateOf("ongoing")
    }

    Scaffold(
        topBar = {
            Column {
                TopBarWithBackButton(
                    text = "Loans",
                    onClose = onClose
                )
                Spacer(modifier = Modifier.height(8.dp))
                LoanScreenTabRow(
                    selectedTabName = selectedTab,
                    onOnGoingClick =
                    {
                        selectedTab = "ongoing"
                    },
                    onApprovalClick =
                    {
                        selectedTab = "approval"
                    },
                    onSettledClick =
                    {
                        selectedTab = "settled"
                    },
                    onCancelledClick =
                    {
                        selectedTab = "cancelled"
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        modifier = modifier
    ) { padding ->
        LoansScreenContent(
            approvalLoanTransactionState = approvalLoanTransactionFlow,
            settledLoanTransactionState = settledLoanTransactionFlow,
            ongoingLoanTransactionState = ongoingLoanTransactionFlow,
            cancelledLoanTransactionState = cancelledLoanTransactionFlow,
            selectedList = selectedTab,
            balanceName = balanceName,
            modifier = Modifier
                .padding(padding)
        )
    }
}

@Composable
fun LoansScreenContent(
    approvalLoanTransactionState: State<List<LoanTransaction>>,
    settledLoanTransactionState: State<List<LoanTransaction>>,
    ongoingLoanTransactionState: State<List<LoanTransaction>>,
    cancelledLoanTransactionState: State<List<LoanTransaction>>,
    selectedList: String,
    balanceName: String,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(4.dp))

    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            LoanTransactionList(
                approvalLoanTransactionState = approvalLoanTransactionState,
                settledLoanTransactionState = settledLoanTransactionState,
                ongoingLoanTransactionState = ongoingLoanTransactionState,
                cancelledLoanTransactionState = cancelledLoanTransactionState,
                selectedList = selectedList,
                balanceName = balanceName
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LoanScreenTabRow(
    selectedTabName: String,
    onOnGoingClick: () -> Unit,
    onApprovalClick: () -> Unit,
    onSettledClick: () -> Unit,
    onCancelledClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = 0,
        indicator = {},
        divider = {},
        modifier = modifier
    ) {
        TopNavigationTab(
            selected = selectedTabName == "ongoing",
            onClick = onOnGoingClick,
            text = "Ongoing",
            modifier = Modifier
        )
        TopNavigationTab(
            selected = selectedTabName == "approval",
            onClick = onApprovalClick,
            text = "Approval",
            modifier = Modifier
        )
        TopNavigationTab(
            selected = selectedTabName == "settled",
            onClick = onSettledClick,
            text = "Settled",
            modifier = Modifier
        )
        TopNavigationTab(
            selected = selectedTabName == "cancelled",
            onClick = onCancelledClick,
            text = "Cancelled",
            modifier = Modifier
        )
    }
}

@Composable
fun LoanTransactionList(
    approvalLoanTransactionState: State<List<LoanTransaction>>,
    settledLoanTransactionState: State<List<LoanTransaction>>,
    ongoingLoanTransactionState: State<List<LoanTransaction>>,
    cancelledLoanTransactionState: State<List<LoanTransaction>>,
    selectedList: String,
    balanceName: String,
    modifier: Modifier = Modifier
) {
    val loanTransactionList =
        when(selectedList) {
            "approval" -> approvalLoanTransactionState.value
            "ongoing" -> ongoingLoanTransactionState.value
            "settled" -> settledLoanTransactionState.value
            "cancelled" -> cancelledLoanTransactionState.value
            else -> ongoingLoanTransactionState.value
        }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(loanTransactionList) { transaction ->
            LoanTransactionItemCard(
                balanceName = balanceName,
                transactionDetails = transaction
            )
        }
    }
}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
fun LoansScreenPreview() {
    PalmHiramTheme {
        Surface {
            LoansScreen(
                role = UserRoles.LENDER,
                borrowerDashboardViewModel = BorrowerDashboardViewModel(null,null),
                lenderDashboardViewModel = LenderDashboardViewModel(null,null),
                onClose = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}
