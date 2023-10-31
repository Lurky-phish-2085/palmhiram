package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.AddHomeWork
import androidx.compose.material.icons.outlined.AirplanemodeActive
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Woman2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ActionButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.Balance
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TwoRowButtonsWithIcon
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.utils.Roles

// TODO: ADD ACTION SECTION
// TODO: Create Composables for elements and style it
// TODO: Create code that will render stuff from the backend
// TODO: Style everything

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(
    role: String,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        modifier = modifier
    ) { paddingValues ->
        OverviewScreenContent(
            amount = "69420.00",
            balanceName =
            when(role) {
                Roles.BORROWER -> borrowerDashboardViewModel?.balanceName!!
                Roles.LENDER -> lenderDashboardViewModel?.balanceName!!
                else -> "HAHA"
            },
            leftButtonName =
            when(role) {
                Roles.BORROWER -> borrowerDashboardViewModel?.leftButtonName!!
                Roles.LENDER -> lenderDashboardViewModel?.leftButtonName!!
                else -> "HAHA"
            },
            rightButtonName =
            when(role) {
                Roles.BORROWER -> borrowerDashboardViewModel?.rightButtonName!!
                Roles.LENDER -> lenderDashboardViewModel?.rightButtonName!!
                else -> "HAHA"
            },
            onLeftButtonClick = { /*TODO*/ },
            onRightButtonClick = { /*TODO*/ },
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun OverviewScreenContent(
    amount: String,
    balanceName: String,
    leftButtonName: String,
    rightButtonName: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
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
                onLeftButtonClick = { /*TODO*/ },
                onRightButtonClick = { /*TODO*/ })
            ActionSection()
            TransactionListSection()
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
    ContentSection {
        BalanceSectionContent(
            onLeftButtonClick = onLeftButtonClick,
            onRightButtonClick = onRightButtonClick,
            leftButtonName = leftButtonName,
            rightButtonName = rightButtonName,
            currencySymbol = currencySymbol,
            amount = amount,
            balanceName = balanceName
        )
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
        items(actions) {
            ActionButton(
                onClick = { /*TODO*/ },
                icon = it.icon,
                actionName = it.actionName,
            )
        }
    }
}

@Composable
fun ActionSection(
    modifier: Modifier = Modifier
) {
    ContentSection {
        ActionListGrid(
            actions = fakeActionItems,
            modifier = modifier
                .fillMaxWidth()

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
fun TransactionListSection() {
    ContentSection {
        PendingTransactionList()
    }
}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
fun OverviewScreenPreview() {
    PalmHiramTheme {
        Surface {
            OverviewScreen(
                role = Roles.BORROWER,
                borrowerDashboardViewModel = BorrowerDashboardViewModel(),
                lenderDashboardViewModel = LenderDashboardViewModel(),
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

data class ActionItem(
    val icon: ImageVector,
    val actionName: String,
)

private val fakeActionItems = listOf(
    ActionItem(
        icon = Icons.Outlined.AddBox,
        actionName = "Add"
    ),
    ActionItem(
        icon = Icons.Outlined.AddHomeWork,
        actionName = "Transactions"
    ),
    ActionItem(
        icon = Icons.Outlined.AirplanemodeActive,
        actionName = "Airplane"
    ),
    ActionItem(
        icon = Icons.Outlined.Inbox,
        actionName = "Inbox"
    ),
    ActionItem(
        icon = Icons.Outlined.Man,
        actionName = "Man"
    ),
    ActionItem(
        icon = Icons.Outlined.Woman2,
        actionName = "Woman"
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