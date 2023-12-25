package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.VerificationCode
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeResponse
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.SearchBarTextField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowerProfilesScreen(
    authViewModel: AuthViewModel?,
    globalState: FunniGlobalViewModel,
    verificationCodeResponseFlow: State<Resource<VerificationCodeResponse>?>?,
    verificationCodeFlow: State<Resource<VerificationCode>?>?,
    retrievedVerificationCodeFlow: State<Resource<VerificationCode>?>?,
    onClose: () -> Unit,
    onSendVerificationCodeViaEmail: () -> Unit,
    onSendVerificationCodeViaSMS: () -> Unit,
    onSendVerificationCodeSuccess: (code: String) -> Unit,
    onSearchValueChange: (searchValue: String) -> Unit,
    onSearchValueSheetChange: (searchValue: String) -> Unit,
    verifiedProfiles: State<List<User>>,
    unVerifiedProfiles: State<List<User>>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var loadingScreenOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()

    var searchFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    var searchFieldValueSheet by rememberSaveable {
        mutableStateOf("")
    }
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isVerifyUserDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    
    val sendVerificationCodeViaEmail = { authViewModel?.sendVerificationCodeEmail() }

    verificationCodeResponseFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                loadingScreenOpen = false

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
            Resource.Loading -> {
                loadingScreenOpen = true
            }
            is Resource.Success -> {
                loadingScreenOpen = false
                
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: code ${it.result.code} sent to email", Toast.LENGTH_SHORT)
                        .show()

                    //authViewModel?.storeVerificationCode(it.result.code)
                    onSendVerificationCodeSuccess(it.result.code)
                }

                //onSendVerificationCodeSuccess(it.result.code)
            }
            else -> {}
        }
    }
    verificationCodeFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                loadingScreenOpen = false
                
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
            Resource.Loading -> {
                loadingScreenOpen = true
            }
            is Resource.Success -> {
                loadingScreenOpen = false

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: code ${it.result.code} stored to DB", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopBarWithBackButton(
                    text = "Borrowers",
                    onClose = onClose,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    IconButton(onClick = { isSheetOpen = true }) {
                        Icon(
                            imageVector = Icons.Rounded.AddCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SearchBarTextField(
                    onValueChange = {
                        searchFieldValue = it
                        onSearchValueChange(searchFieldValue)
                    },
                    value = searchFieldValue,
                    placeholder = "Search for verified borrower clients",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 16.dp)
                )

                if (listState.firstVisibleItemScrollOffset != 0) Divider()
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BorrowerProfileList(
                        onClickItem = { /*TODO*/ },
                        globalState = globalState,
                        state = listState,
                        profiles = verifiedProfiles.value,
                    )
                }
            }
        }
    }

    VerifyUsersBottomSheet(
        sheetState = sheetState,
        globalState = globalState,
        isOpen = isSheetOpen,
        profiles = unVerifiedProfiles,
        onDismissRequest = { isSheetOpen = false },
        onClickItem = {
            isSheetOpen = false
            isVerifyUserDialogOpen = true
        },
        onSearchValueChange = {
            searchFieldValueSheet = it
            onSearchValueSheetChange(it)
        },
    )

    VerifyUserDialog(
        isOpen = isVerifyUserDialogOpen,
        onDismissRequest = {
            isVerifyUserDialogOpen = false
            isSheetOpen = true
        },
        onClose = {
            isVerifyUserDialogOpen = false
            isSheetOpen = true
        },
        onSendViaEmail = {
            onSendVerificationCodeViaEmail()
            //authViewModel?.sendVerificationCodeEmail()
            Log.e("CALLED", "BorrowerProfilesScreen: CLICKED SEND VER CODE EMAIL")
            isVerifyUserDialogOpen = false
        },
        onSendViaSMS = {
            onSendVerificationCodeViaSMS()
            isVerifyUserDialogOpen = false
        },
        userDetails = globalState.selectedUserProfileItem
    )

    CircularProgressLoadingIndicator(isOpen = loadingScreenOpen)
}

@Composable
fun BorrowerProfileItemCard(
    onClick: () -> Unit,
    details: User,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = MaterialTheme.shapes.small,
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
    globalState: FunniGlobalViewModel,
    onClickItem: () -> Unit,
    state: LazyListState = rememberLazyListState(),
    profiles: List<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = modifier
    ) {
        items(profiles, key = { it.id }) { profile ->
            BorrowerProfileItemCard(
                onClick = {
                    globalState.selectedUserProfileItem = profile
                    onClickItem()
                },
                details = User(
                    name = profile.name,
                    email = profile.email,
                    phone = profile.phone,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyUsersBottomSheet(
    globalState: FunniGlobalViewModel,
    isOpen: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(),
    profiles: State<List<User>>,
    onDismissRequest: () -> Unit,
    onClickItem: () -> Unit,
    onSearchValueChange: (searchValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    var searchFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    if (isOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            modifier = modifier
        ) {
            Scaffold(
                topBar = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose which accounts to verify",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp)
                        )

                        SearchBarTextField(
                            onValueChange = {
                                searchFieldValue = it
                                onSearchValueChange(it)
                            },
                            value = searchFieldValue,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .padding(horizontal = 16.dp)
                        )

                        if (lazyListState.firstVisibleItemScrollOffset > 0) Divider()
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
            ) { paddingValues ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    BorrowerProfileList(
                        globalState = globalState,
                        onClickItem = onClickItem,
                        state = lazyListState,
                        profiles = profiles.value,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VerifyUserDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClose: () -> Unit,
    onSendViaEmail: () -> Unit,
    onSendViaSMS: () -> Unit,
    userDetails: User,
    modifier: Modifier = Modifier
) {
    if (isOpen)
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                shape = MaterialTheme.shapes.large,
                modifier = modifier
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)) {
                        Text(
                            text = "Verify User",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                        )

                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                    )
                    Text(
                        text = userDetails.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        text = userDetails.email,
                        style = MaterialTheme.typography.bodyLarge
                            .copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.outline
                            )
                    )
                    Text(
                        text = userDetails.phone,
                        style = MaterialTheme.typography.bodyLarge
                            .copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.outline
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Send code via:")

                    Button(onClick = onSendViaEmail) {
                        Text(
                            text = "Send via Email",
                        )
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Send via SMS"
                        )
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "light - Borrower", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun VerifyBorrowerBottomSheetPreview() {
    PalmHiramTheme {
        Surface {
            var profilesFlow = fakeData2
            var profilesState = profilesFlow.collectAsState()

            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }

            val sheetState = rememberModalBottomSheetState()

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(onClick = { isSheetOpen = true }) {
                        Text(text = "Open sheet")
                    }
                }

                VerifyUsersBottomSheet(
                    globalState = FunniGlobalViewModel(),
                    onClickItem = {},
                    isOpen = isSheetOpen,
                    sheetState = sheetState,
                    profiles = profilesState,
                    onDismissRequest = { isSheetOpen = false },
                    onSearchValueChange = {}
                )
            }
        }
    }
}

@Preview(name = "light - Borrower", showBackground = true, uiMode = UI_MODE_NIGHT_NO, heightDp = 512, widthDp = 512)
@Composable
fun VerifyUserDialogPreview() {
    PalmHiramTheme {
        Surface {
            var isDialogOpen by rememberSaveable {
                mutableStateOf(true)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                VerifyUserDialog(
                    isOpen = isDialogOpen,
                    onDismissRequest = { isDialogOpen = false},
                    onClose = { isDialogOpen = false },
                    onSendViaEmail = { isDialogOpen = false },
                    onSendViaSMS = { isDialogOpen = false },
                    userDetails = User(
                        id = "234823u   i38",
                        name = "Zhalou Chua",
                        email = "skdkbok1234@gmail.com",
                        phone = "09988776655"
                    ),
                )
            }
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
            val flow = fakeData1
            val state = flow.collectAsState()

            BorrowerProfilesScreen(
                globalState = FunniGlobalViewModel(),
                authViewModel = null,
                verificationCodeResponseFlow = null,
                verificationCodeFlow = null,
                retrievedVerificationCodeFlow = null,
                onClose = {},
                onSendVerificationCodeViaEmail = {},
                onSendVerificationCodeViaSMS = {},
                onSendVerificationCodeSuccess = {},
                onSearchValueChange = {},
                onSearchValueSheetChange = {},
                verifiedProfiles = state,
                unVerifiedProfiles = state,
            )
        }
    }
}

private val fakeData1 = MutableStateFlow<List<User>>(
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

private val fakeData2 = MutableStateFlow<List<User>>(
    listOf(
        User(
            id = "234822",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492jsdkfjdk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823ui3dasf8",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234929kskssk",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "28disjf23492jdk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823ui323isj8",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234jcnsoqo",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492jdk9ksks",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823ui3nxnxnx8",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "2iwkska34",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492jd99ik2ws[nk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "hxn234823ui38",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234**&*&*&",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492jbbjddk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "ksnxl234823ui38",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234-0--=--9jk",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492ksjlknkjdk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "ksn928&&^234823ui38",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "2sijsj    4",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492j0298uiokdk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823ui3oobo8",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "jksjx9234",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "23492jdxnnxnxxnk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823ui3-okl;mkmvcx x8",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234  ",
            name = "Dok Chuang",
            email = "dok1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "  23492jdk",
            name = "Bing Choy",
            email = "bing1234@gmail.com",
            phone = "09988776655"
        ),
        User(
            id = "234823u   i38",
            name = "Zhalou Chua",
            email = "skdkbok1234@gmail.com",
            phone = "09988776655"
        ),
    ),
)