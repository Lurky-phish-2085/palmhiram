package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.AddHomeWork
import androidx.compose.material.icons.outlined.AirplanemodeActive
import androidx.compose.material.icons.outlined.Dataset
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Woman2
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ActionButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.Balance
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.PagerScrollIndicatorDots
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TwoRowButtonsWithIcon
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.ActionButtonTypes
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.capitalized
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles

// TODO: ADD ACTION SECTION
// TODO: Create Composables for elements and style it
// TODO: Create code that will render stuff from the backend
// TODO: Style everything

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(
    globalState: FunniGlobalViewModel,
    onLeftButtonClickAsBorrower: () -> Unit,
    onRightButtonClickAsBorrower: () -> Unit,
    onLeftButtonClickAsLender: () -> Unit,
    onRightButtonClickAsLender: () -> Unit,
    onLoansClickAsLender: () -> Unit,
    onLoansClickAsBorrower: () -> Unit,
    onTransactionsClickAsLender: () -> Unit,
    onTransactionsClickAsBorrower: () -> Unit,
    onProfilesClickAsLender: () -> Unit,
    onSelectLoanTransactionItemAsLender: () -> Unit,
    onSelectLoanTransactionItemAsBorrower: () -> Unit,
    role: UserRoles,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    modifier: Modifier = Modifier
) {
    val balanceAmountRef: StateFlow<Money> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.totalPayablesBalance!!
            UserRoles.LENDER -> lenderDashboardViewModel?.totalCollectBalance!!
        }

    val balanceAmountFlow = balanceAmountRef.collectAsState()

    val ongoingLoanTransactionListRef: StateFlow<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.ongoingLoanTransactions!!
            UserRoles.LENDER -> lenderDashboardViewModel?.ongoingLoanTransactions!!
        }
    val approvalLoanTransactionListRef: StateFlow<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.approvalLoanTransactions!!
            UserRoles.LENDER -> lenderDashboardViewModel?.approvalLoanTransactions!!
        }
    val borrowerApprovalLoanTransactionListRef: StateFlow<List<LoanTransaction>> =
        when(role) {
            UserRoles.BORROWER -> borrowerDashboardViewModel?.borrowerApprovalLoanTransactions!!
            UserRoles.LENDER -> lenderDashboardViewModel?.borrowerApprovalLoanTransactions!!
        }

    val ongoingLoanTransactionListFlow = ongoingLoanTransactionListRef.collectAsState()
    val approvalLoanTransactionListFlow = approvalLoanTransactionListRef.collectAsState()
    val borrowerApprovalTransactionListFlow = borrowerApprovalLoanTransactionListRef.collectAsState()

    Scaffold(
        topBar = {},
        bottomBar = {},
        modifier = modifier
    ) { paddingValues ->
        OverviewScreenContent(
            globalState = globalState,
            amount = balanceAmountFlow.value.toString(),
            ongoingTransactionList = ongoingLoanTransactionListFlow.value,
            approvalTransactionList = approvalLoanTransactionListFlow.value,
            borrowerApprovalTransactionList = borrowerApprovalTransactionListFlow.value,
            balanceName =
                when(role) {
                    UserRoles.BORROWER -> borrowerDashboardViewModel?.balanceName!!
                    UserRoles.LENDER -> lenderDashboardViewModel?.balanceName!!
                },
            leftButtonName =
                when(role) {
                    UserRoles.BORROWER -> borrowerDashboardViewModel?.leftButtonName!!
                    UserRoles.LENDER -> lenderDashboardViewModel?.leftButtonName!!
                },
            rightButtonName =
                when(role) {
                    UserRoles.BORROWER -> borrowerDashboardViewModel?.rightButtonName!!
                    UserRoles.LENDER -> lenderDashboardViewModel?.rightButtonName!!
                },
            onLeftButtonClick =
                when(role) {
                    UserRoles.BORROWER -> onLeftButtonClickAsBorrower
                    UserRoles.LENDER -> onLeftButtonClickAsLender
                },
            onRightButtonClick =
                when(role) {
                    UserRoles.BORROWER -> onRightButtonClickAsBorrower
                    UserRoles.LENDER -> onRightButtonClickAsLender
                },
            actionItems =
                when(role) {
                    UserRoles.BORROWER -> borrowerActionItems
                    UserRoles.LENDER -> lenderActionItems
                },
            onLoansClick =
                when(role) {
                    UserRoles.BORROWER -> onLoansClickAsBorrower
                    UserRoles.LENDER -> onLoansClickAsLender
               },
            onTransactionsClick =
                when(role) {
                    UserRoles.BORROWER -> onTransactionsClickAsBorrower
                    UserRoles.LENDER -> onTransactionsClickAsLender
                },
            onProfilesClick = onProfilesClickAsLender,
            onSelectLoanTransactionPager =
                when(role) {
                    UserRoles.BORROWER -> onSelectLoanTransactionItemAsBorrower
                    // TODO: Add the nav for the lender
                     UserRoles.LENDER ->  onSelectLoanTransactionItemAsLender
                },
            role = role,
            borrowerDashboardViewModel = borrowerDashboardViewModel,
            lenderDashboardViewModel = lenderDashboardViewModel,
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun OverviewScreenContent(
    globalState: FunniGlobalViewModel,
    amount: String,
    balanceName: String,
    ongoingTransactionList: List<LoanTransaction>,
    approvalTransactionList: List<LoanTransaction>,
    borrowerApprovalTransactionList: List<LoanTransaction>,
    leftButtonName: String,
    rightButtonName: String,
    actionItems: List<ActionItem>,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    onLoansClick: () -> Unit,
    onTransactionsClick: () -> Unit,
    onProfilesClick: () -> Unit,
    onSelectLoanTransactionPager: () -> Unit,
    role: UserRoles,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            BalanceSection(
                currencySymbol = '₱',
                amount = amount,
                balanceName = balanceName,
                leftButtonName = leftButtonName,
                rightButtonName = rightButtonName,
                onLeftButtonClick = onLeftButtonClick,
                onRightButtonClick = onRightButtonClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            LoanTransactionsHorizontalPager(
                globalState = globalState,
                onClickItem = onSelectLoanTransactionPager,
                transactionList = ongoingTransactionList,
                balanceName = balanceName,
                role = role,
                borrowerDashboardViewModel = borrowerDashboardViewModel,
                lenderDashboardViewModel = lenderDashboardViewModel,
                modifier = Modifier
                    .fillMaxWidth()
            )
            LoanTransactionsHorizontalPager(
                globalState = globalState,
                onClickItem = onSelectLoanTransactionPager,
                transactionList = approvalTransactionList,
                balanceName = balanceName,
                role = role,
                borrowerDashboardViewModel = borrowerDashboardViewModel,
                lenderDashboardViewModel = lenderDashboardViewModel,
                modifier = Modifier
                    .fillMaxWidth()
            )
            LoanTransactionsHorizontalPager(
                globalState = globalState,
                onClickItem = { /*TODO*/ },
                role = role,
                borrowerDashboardViewModel = borrowerDashboardViewModel,
                lenderDashboardViewModel = lenderDashboardViewModel,
                transactionList = borrowerApprovalTransactionList,
                balanceName = balanceName
            )
            ActionSection(
                onLoansClick = onLoansClick,
                onTransactionsClick = onTransactionsClick,
                onProfilesClick = onProfilesClick,
                actionItems = actionItems,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            TransactionListSection(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun BalanceSection(
    currencySymbol: Char,
    amount: String,
    balanceName: String,
    leftButtonName: String,
    rightButtonName: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ContentSection() {
            BalanceSectionContent(
                onLeftButtonClick = onLeftButtonClick,
                onRightButtonClick = onRightButtonClick,
                leftButtonName = leftButtonName,
                rightButtonName = rightButtonName,
                currencySymbol = currencySymbol,
                amount = amount,
                balanceName = balanceName,
            )
        }
    }
}

@Composable
fun BalanceSectionContent(
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    currencySymbol: Char,
    amount: String,
    balanceName: String,
    leftButtonName: String,
    rightButtonName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Balance(
            amount = amount,
            currencySymbol = currencySymbol
        )
        Text(
            text = balanceName,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TwoRowButtonsWithIcon(
            onClickLeft = onLeftButtonClick,
            onClickRight = onRightButtonClick,
            leftIcon = Icons.Default.ArrowDownward,
            rightIcon = Icons.Default.ArrowOutward,
            leftContent = {
                Text(
                    text = leftButtonName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            },
            rightContent = {
                Text(
                    text = rightButtonName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        )
    }
}

@Composable
fun ActionListGrid(
    onLoansClick: () -> Unit,
    onTransactionsClick: () -> Unit,
    onProfilesClick: () -> Unit,
    actions: List<ActionItem>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 64.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .height(180.dp)
    ) {
        items(actions) { item ->
            ActionButton(
                onClick =
                    when(item.type) {
                        ActionButtonTypes.LOANS -> onLoansClick
                        ActionButtonTypes.TRANSACTIONS -> onTransactionsClick
                        ActionButtonTypes.PROFILES -> onProfilesClick
                    },
                icon = item.icon,
                actionName = item.actionName,
            )
        }
    }
}

@Composable
fun ActionSection(
    onLoansClick: () -> Unit,
    onTransactionsClick: () -> Unit,
    onProfilesClick: () -> Unit,
    actionItems: List<ActionItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ContentSection {
            ActionListGrid(
                onLoansClick = onLoansClick,
                onTransactionsClick = onTransactionsClick,
                onProfilesClick = onProfilesClick,
                actions = actionItems,
                modifier = modifier
                    .fillMaxWidth()
                    .height(128.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoanTransactionsHorizontalPager(
    globalState: FunniGlobalViewModel,
    onClickItem: () -> Unit,
    role: UserRoles,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    transactionList: List<LoanTransaction>,
    balanceName: String,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { transactionList.size })
    if (transactionList.isNotEmpty()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(all = 8.dp),
            pageSpacing = 3.dp,
            modifier = modifier
        ) { index ->
            LoanTransactionItemCard(
                // TODO: Go to the designated screen if a borrower clicked this
                onClick = {
                    globalState.selectedLoanTransactionItem = transactionList[index]
                    onClickItem()
                },
                balanceName = balanceName,
                transactionDetails = transactionList[index],
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        PagerScrollIndicatorDots(
            totalDots = transactionList.size,
            selectedIndex = pagerState.currentPage,
            selectedColor = MaterialTheme.colorScheme.onBackground,
            unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun PendingTransactionItemElement(
    amount: String,
    currencySymbol: Char,
    status: String,
    dueDate: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row {
            Text(
                text = "Amount",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
            )
            Spacer(modifier = Modifier.weight(1f, true))
            Text(
                text = "Due on",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
            )
        }
        Row {
            Text(text = "$currencySymbol $amount")
            Spacer(modifier = Modifier.weight(1f, true))
            Text(text = dueDate)
        }
    }
}

@Composable
fun PendingTransactionList(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row() {
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.weight(1f, true))
            ClickableText(
                text = AnnotatedString(
                    text = "See all",
                    spanStyle = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                ),
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (item in fakeTransactionItemData) {
                PendingTransactionItemElement(
                    currencySymbol = '₱',
                    amount = item.amount,
                    dueDate = item.dueDate,
                    status = item.status,
                )
            }
        }
    }
}

@Composable
fun TransactionListSection(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ContentSection {
            PendingTransactionList()
        }
    }
}

@Preview(name = "light - borrower", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@ExperimentalMaterial3Api
@Composable
fun OverviewScreenBorrowerPreview() {
    PalmHiramTheme {
        Surface {
            OverviewScreen(
                globalState = FunniGlobalViewModel(),
                role = UserRoles.BORROWER,
                borrowerDashboardViewModel = BorrowerDashboardViewModel(null, null),
                lenderDashboardViewModel = LenderDashboardViewModel(null, null, null),
                onLeftButtonClickAsBorrower = {},
                onLeftButtonClickAsLender = {},
                onRightButtonClickAsBorrower = {},
                onRightButtonClickAsLender = {},
                onLoansClickAsBorrower = {},
                onLoansClickAsLender = {},
                onTransactionsClickAsBorrower = {},
                onTransactionsClickAsLender = {},
                onProfilesClickAsLender = {},
                onSelectLoanTransactionItemAsBorrower = {},
                onSelectLoanTransactionItemAsLender = {}
            )
        }
    }
}

@Preview(name = "light - lender", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@ExperimentalMaterial3Api
@Composable
fun OverviewScreenLenderPreview() {
    PalmHiramTheme {
        Surface {
            OverviewScreen(
                globalState = FunniGlobalViewModel(),
                role = UserRoles.LENDER,
                borrowerDashboardViewModel = BorrowerDashboardViewModel(null, null),
                lenderDashboardViewModel = LenderDashboardViewModel(null, null, null),
                onLeftButtonClickAsBorrower = {},
                onLeftButtonClickAsLender = {},
                onRightButtonClickAsBorrower = {},
                onRightButtonClickAsLender = {},
                onLoansClickAsBorrower = {},
                onLoansClickAsLender = {},
                onTransactionsClickAsBorrower = {},
                onTransactionsClickAsLender = {},
                onProfilesClickAsLender = {},
                onSelectLoanTransactionItemAsBorrower = {},
                onSelectLoanTransactionItemAsLender = {},
            )
        }
    }
}

data class ActionItem(
    val icon: ImageVector,
    val actionName: String,
    val type: ActionButtonTypes,
)

private val borrowerActionItems = listOf(
    ActionItem(
        icon = Icons.Outlined.Money,
        actionName = ActionButtonTypes.LOANS.toString().lowercase().capitalized(),
        type = ActionButtonTypes.LOANS,
    ),
    ActionItem(
        icon = Icons.Outlined.Dataset,
        actionName = ActionButtonTypes.TRANSACTIONS.toString().lowercase().capitalized(),
        type = ActionButtonTypes.TRANSACTIONS,
    ),
)

private val lenderActionItems = listOf(
    ActionItem(
        icon = Icons.Outlined.Money,
        actionName = ActionButtonTypes.LOANS.toString().lowercase().capitalized(),
        type = ActionButtonTypes.LOANS,
    ),
    ActionItem(
        icon = Icons.Outlined.Dataset,
        actionName = ActionButtonTypes.TRANSACTIONS.toString().lowercase().capitalized(),
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.AccountBox,
        actionName = ActionButtonTypes.PROFILES.toString().lowercase().capitalized(),
        type = ActionButtonTypes.PROFILES
    )
)

private val fakeActionItems = listOf(
    ActionItem(
        icon = Icons.Outlined.AddBox,
        actionName = "Add",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.AddHomeWork,
        actionName = "Transactions",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.AirplanemodeActive,
        actionName = "Airplane",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.Inbox,
        actionName = "Inbox",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.Man,
        actionName = "Man",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
    ActionItem(
        icon = Icons.Outlined.Woman2,
        actionName = "Woman",
        type = ActionButtonTypes.TRANSACTIONS,
    ),
)

data class FakeTransactionItem(
    val amount: String,
    val dueDate: String,
    val status: String,
)

private val fakeTransactionItemData = listOf(
    FakeTransactionItem(
        "69420.00",
        "22 Apr 2023",
        "Pending",
    ),
    FakeTransactionItem(
        "69420.00",
        "22 Apr 2023",
        "Pending",
    ),
    FakeTransactionItem(
        "69.00",
        "22 Jan 2023",
        "Paid",
    ),
    FakeTransactionItem(
        "420.00",
        "22 Apr 2023",
        "Paid",
    ),
    FakeTransactionItem(
        "69420.00",
        "22 Apr 2023",
        "Pending",
    )
)