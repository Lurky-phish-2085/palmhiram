package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
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
    // TODO: VIEWMODEL FOR BORROWER OR LENDER BASE ON ROLE

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Loans",
                onClose = onClose
            )
        },
        modifier = modifier
    ) { padding ->
        LoansScreenContent(
            modifier = Modifier
                .padding(padding)
        )
    }
}

@Composable
fun LoansScreenContent(
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(4.dp))

    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoanTransactionItemCard(
                balanceName = "Total amount to pay",
                transactionDetails = LoanTransaction(
                    borrowerName = "Mal Boros",
                    status = LoanTransactionStatus.APPROVED.toString(),
                    totalBalance = 69420,
                    endDate = Timestamp.now(),
                ),
            )
            LoanTransactionItemCard(
                balanceName = "Total amount to pay",
                transactionDetails = LoanTransaction(
                    borrowerName = "Mal Boros",
                    status = LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER.toString(),
                    principalAmount = 69240,
                    totalBalance = 69420,
                    totalPayment = 0,
                    startDate = Timestamp.now(),
                    endDate = null,
                ),
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
                    .padding(all = 16.dp)
            )
        }
    }
}
