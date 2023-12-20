package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowerProfilesScreen(
    onClose: () -> Unit,
    profiles: State<List<User>>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Borrowers",
                onClose = onClose
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BorrowerProfileList(
                        profiles = profiles.value,
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun BorrowerProfileItemCard(
    onClick: () -> Unit,
    details: User,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = details.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Text(
                    text = details.email,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.outline
                        )
                )
                Text(
                    text = details.phone,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.outline
                        )
                )
            }
        }
    }
}
@Composable
fun BorrowerProfileList(
    profiles: List<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = modifier
    ) {
        items(profiles, key = { it.id }) { profile ->
            BorrowerProfileItemCard(
                onClick = { /*TODO*/ },
                details = User(
                    name = profile.name,
                    email = profile.email,
                    phone = profile.phone,
                )
            )
        }
    }
}

@Preview
@Composable
fun BorrowerProfileItemCardPreview() {
    PalmHiramTheme {
        Surface {
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
            ) {
                BorrowerProfileItemCard(
                    onClick = {},
                    details = User(
                        name = "Bon Appetit",
                        email = "bonappetit123@gmail.com",
                        phone = "09988776655"
                    ),
                )
            }
        }
    }
}

@Preview(name = "light - Borrower", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun BorrowerProfilesScreenPreview() {
    PalmHiramTheme {
        Surface {
            val flow =
                MutableStateFlow<List<User>>(
                    listOf(
                        User(
                            id = "1",
                            name = "Bon Appetit",
                            email = "bonappetit123@gmail.com",
                            phone = "09988776655"
                        ),
                        User(
                            id = "2",
                            name = "Bon Appetit",
                            email = "bonappetit123@gmail.com",
                            phone = "09988776655"
                        ),
                        User(
                            id = "3",
                            name = "Bon Appetit",
                            email = "bonappetit123@gmail.com",
                            phone = "09988776655"
                        ),
                        User(
                            name = "Bon Appetit",
                            email = "bonappetit123@gmail.com",
                            phone = "09988776655"
                        ),
                    ),
                )

            val state = flow.collectAsState()

            BorrowerProfilesScreen(
                onClose = {},
                profiles = state,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
        }
    }
}