package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NothingToSeeHere
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopNavigationTab
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun LoansScreen(
    onClickItemAsBorrower: () -> Unit,
    onClickItemAsLender: () -> Unit,
    globalState: FunniGlobalViewModel,
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

    var selectedTabName by rememberSaveable {
        mutableStateOf("ongoing")
    }
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            Column {
                TopBarWithBackButton(
                    text = "Loans",
                    onClose = onClose,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LoanScreenTabRow(
                    selectedTabIndex = selectedTabIndex,
                    selectedTabName = selectedTabName,
                    onOnGoingClick =
                    {
                        selectedTabIndex = 0
                        selectedTabName = "ongoing"
                    },
                    onApprovalClick =
                    {
                        selectedTabIndex = 1
                        selectedTabName = "approval"
                    },
                    onSettledClick =
                    {
                        selectedTabIndex = 2
                        selectedTabName = "settled"
                    },
                    onCancelledClick =
                    {
                        selectedTabIndex = 3
                        selectedTabName = "cancelled"
                    },
                )
            }
        },
        modifier = modifier
    ) { padding ->
        LoansScreenContent(
            onClickItem =
            when (role) {
                UserRoles.BORROWER -> onClickItemAsBorrower
                UserRoles.LENDER -> onClickItemAsLender
            },
            globalState = globalState,
            approvalLoanTransactionState = approvalLoanTransactionFlow,
            settledLoanTransactionState = settledLoanTransactionFlow,
            ongoingLoanTransactionState = ongoingLoanTransactionFlow,
            cancelledLoanTransactionState = cancelledLoanTransactionFlow,
            selectedList = selectedTabName,
            balanceName = balanceName,
            modifier = Modifier
                .padding(padding)
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun LoansScreenContent(
    onClickItem: () -> Unit,
    globalState: FunniGlobalViewModel,
    approvalLoanTransactionState: State<List<LoanTransaction>>,
    settledLoanTransactionState: State<List<LoanTransaction>>,
    ongoingLoanTransactionState: State<List<LoanTransaction>>,
    cancelledLoanTransactionState: State<List<LoanTransaction>>,
    selectedList: String,
    balanceName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            LoanTransactionList(
                onClickItem = onClickItem,
                globalState = globalState,
                approvalLoanTransactionState = approvalLoanTransactionState,
                settledLoanTransactionState = settledLoanTransactionState,
                ongoingLoanTransactionState = ongoingLoanTransactionState,
                cancelledLoanTransactionState = cancelledLoanTransactionState,
                selectedList = selectedList,
                balanceName = balanceName,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LoanScreenTabRow(
    selectedTabIndex: Int = 0,
    selectedTabName: String,
    onOnGoingClick: () -> Unit,
    onApprovalClick: () -> Unit,
    onSettledClick: () -> Unit,
    onCancelledClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
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

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun LoanTransactionList(
    onClickItem: () -> Unit,
    globalState: FunniGlobalViewModel,
    approvalLoanTransactionState: State<List<LoanTransaction>>,
    settledLoanTransactionState: State<List<LoanTransaction>>,
    ongoingLoanTransactionState: State<List<LoanTransaction>>,
    cancelledLoanTransactionState: State<List<LoanTransaction>>,
    selectedList: String,
    balanceName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val loanTransactionList =
        when(selectedList) {
            "approval" -> approvalLoanTransactionState.value
            "ongoing" -> ongoingLoanTransactionState.value
            "settled" -> settledLoanTransactionState.value
            "cancelled" -> cancelledLoanTransactionState.value
            else -> ongoingLoanTransactionState.value
        }

    AnimatedContent(
        targetState = selectedList,
        label = "AnimatedAppearanceOfList",
        transitionSpec = {
            slideInVertically { height -> height }  togetherWith
                    ExitTransition.None
        },
    ) {
        if (loanTransactionList.isEmpty()) NothingToSeeHere(Modifier.fillMaxSize()) else
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = modifier
            ) {
                items(loanTransactionList, key = { it.id }) { transaction ->
                    LoanTransactionItemCard(
                        onClick = {
                            globalState.selectedLoanTransactionItem = transaction
                            onClickItem()
                            //TODO: Navigate to detail screen or something
                            Log.e("LoanT Item Select", "LoanTransactionList: ${transaction.totalBalance}")
                        },
                        balanceName = balanceName,
                        transactionDetails = transaction,
                        modifier = Modifier
                            .animateItemPlacement()
                    )
                }
            }
    }
}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun LoansScreenPreview() {
    PalmHiramTheme {
        Surface {
            LoansScreen(
                onClickItemAsBorrower = {},
                onClickItemAsLender = {},
                globalState = FunniGlobalViewModel(),
                role = UserRoles.LENDER,
                borrowerDashboardViewModel = BorrowerDashboardViewModel(null,null),
                lenderDashboardViewModel = LenderDashboardViewModel(null,null, null),
                onClose = {},
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
    }
}
