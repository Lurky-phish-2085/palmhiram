package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton


@Composable
fun BorrowerLoanProfileScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopBarWithBackButton(
            text = "Borrower Loan Profile",
            onClose = onClose,
        )

        Text(
            text = "Borrower Name: ",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Total amount to collect: ",
            style = MaterialTheme.typography.headlineLarge
        )


        Text(
            text = "Pending Loan",
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = modifier
        ) {

        }

        Text(
            text = "Settled Loans",
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = modifier
        ) {

        }

        Text(
            text = "Cancelled Loans",
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = modifier
        ) {

        }
    }
}