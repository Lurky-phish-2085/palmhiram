package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

@Composable
fun LoanTransactionItemCard(
    currencySymbol: Char = '₱',
    balanceName: String,
    transactionDetails: LoanTransaction,
    modifier: Modifier = Modifier
        .padding(horizontal = 2.dp)
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { /*TODO*/ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
            )
            Text(
                text = transactionDetails.borrowerName,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline
                ),
            )

            Spacer(modifier = Modifier.weight(1f, true))

            Text(
                text =
                when(LoanTransactionStatus.valueOf(transactionDetails.status.uppercase())) {
                    LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER -> "APPROVAL"
                    LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_BORROWER -> "APPROVAL"
                    LoanTransactionStatus.APPROVED -> "ONGOING"
                    LoanTransactionStatus.SETTLED -> "SETTLED"
                    LoanTransactionStatus.CANCELLED -> "CANCELLED"
                },
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color =
                    when(LoanTransactionStatus.valueOf(transactionDetails.status.uppercase())) {
                        LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER -> MaterialTheme.colorScheme.error
                        LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_BORROWER -> MaterialTheme.colorScheme.error
                        LoanTransactionStatus.APPROVED -> MaterialTheme.colorScheme.secondary
                        LoanTransactionStatus.SETTLED -> MaterialTheme.colorScheme.primary
                        LoanTransactionStatus.CANCELLED -> MaterialTheme.colorScheme.error
                    }
                ),
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "$currencySymbol ${Money.parseActualValue(transactionDetails.totalBalance)}",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = balanceName,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = DateTimeUtils.formatToISO8601Date(transactionDetails.endDate?.toDate()!!),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            ContentSection {
                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DETAILS",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = transactionDetails.toString())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(name = "light - Lender", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoanTransactionItemCardLenderPreview() {
    PalmHiramTheme {
        Surface {
            Column(
                modifier = Modifier.padding(all = 8.dp)
            ) {
                LoanTransactionItemCard(
                    balanceName = "Total amount to collect",
                    transactionDetails = LoanTransaction(
                        borrowerName = "Mal Boros",
                        status = LoanTransactionStatus.APPROVED.toString(),
                        totalBalance = 69420,
                        endDate = Timestamp.now(),
                    ),
                )
            }
        }
    }
}

@Preview(name = "light - Borrower", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoanTransactionItemCardBorrowerPreview() {
    PalmHiramTheme {
        Surface {
            Column(
                modifier = Modifier.padding(all = 8.dp)
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
            }
        }
    }
}
