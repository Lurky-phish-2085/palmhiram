package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

@Composable
fun PaymentScheduleDateItemCard(
    onClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    details: PaymentScheduleDate,
    modifier: Modifier = Modifier
) {
    val a = details.date?.toDate()
    val b = DateTimeUtils.formatToISO8601NullDate(a)

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = b ?: "Null LMAO XD" , modifier = Modifier.align(Alignment.CenterStart))

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Text(text = "${Money.parseActualValue(globalState.selectedLoanTransactionItem.paymentPerSchedule)}")
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
            }
        }
    }
}
