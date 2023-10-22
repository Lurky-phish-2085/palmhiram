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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowOutward
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.Balance
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TwoRowButtonsWithIcon
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

// TODO: ADD ACTION SECTION
// TODO: Create Composables for elements and style it
// TODO: Create code that will render stuff from the backend
// TODO: Style everything

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        modifier = modifier
    ) { paddingValues ->
        OverviewScreenContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun OverviewScreenContent(
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
            BalanceSection(
                currencySymbol = '₱',
                amount = "69420",
                balanceName = "Total amount to pay",
                leftButtonName = "Apply Loan",
                rightButtonName = "Pay Loan",
                onLeftButtonClick = { /*TODO*/ },
                onRightButtonClick = { /*TODO*/ })
            TransactionListSection()
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
    OverviewSection {
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
            style = MaterialTheme.typography.labelLarge
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
fun TransactionItemElement(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row {
            Text(text = "Paid Loan")
            Spacer(modifier = Modifier.weight(1f, true))
            Text(text = "22 Apr 2023")
        }
        Row {
            Text(text = "Lender")
            Spacer(modifier = Modifier.weight(1f, true))
            Text(text = "P 69420.00")
        }
    }
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row() {
            Text(
                text = "Transactions",
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
                TransactionItemElement()
            }
        }
    }
}

@Composable
fun OverviewSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun TransactionListSection() {
    OverviewSection {
        TransactionList()
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
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

data class FakeTransactionItem(
    val activity: String,
    val recipient: String,
    val amount: String,
)

private val fakeTransactionItemData = listOf(
    FakeTransactionItem(
        "Gaming",
        "GBoy",
        "69420.00"
    ),
    FakeTransactionItem(
        "Gaming",
        "GBoy",
        "69420.00"
    ),
    FakeTransactionItem(
        "Gaming",
        "GBoy",
        "69420.00"
    ),
    FakeTransactionItem(
        "Gaming",
        "GBoy",
        "69420.00"
    ),
    FakeTransactionItem(
        "Gaming",
        "GBoy",
        "69420.00"
    ),
)