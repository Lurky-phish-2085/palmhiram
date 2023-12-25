package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel

@Composable
fun BorrowerProfilesScreenRoute(
    globalState: FunniGlobalViewModel,
    authViewModel: AuthViewModel,
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    val verifiedProfilesFlow = lenderDashboardViewModel.verifiedBorrowerUserProfiles.collectAsState()
    val unVerifiedProfilesFlow = lenderDashboardViewModel.unVerifiedBorrowerUserProfiles.collectAsState()

    val verificationCodeFlow = authViewModel.verificationCodeFlow.collectAsState()
    val retrievedVerificationCodeFlow = authViewModel.retrievedVerificationCodeFlow.collectAsState()
    val verificationCodeResponseFlow = authViewModel.verificationCodeResponseFlow.collectAsState()

    var verificationCode by rememberSaveable {
        mutableStateOf("")
    }

    var sendVerificationCodeEmail by rememberSaveable {
        mutableStateOf(false)
    }
    var storeVerificationCode by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(sendVerificationCodeEmail) {
        if (sendVerificationCodeEmail) {

            val sendVerificationCodeEmail1 = authViewModel.sendVerificationCodeEmail(
                globalState.selectedUserProfileItem.name,
                globalState.selectedUserProfileItem.email
            )

            sendVerificationCodeEmail = false
        }
    }

    LaunchedEffect(storeVerificationCode) {
        if (sendVerificationCodeEmail) {
            authViewModel.storeVerificationCode(verificationCode)
            storeVerificationCode = false
        }
    }

    BorrowerProfilesScreen(
        globalState = globalState,
        authViewModel = authViewModel,
        verificationCodeResponseFlow = verificationCodeResponseFlow,
        verificationCodeFlow = verificationCodeFlow,
        retrievedVerificationCodeFlow = retrievedVerificationCodeFlow,
        onClose = onClose,
        onSendVerificationCodeViaEmail = {
            Log.e("CALLED", "BorrowerProfilesScreenRoute: SEND VER CODE EMAIL")
            sendVerificationCodeEmail = true
        },
        onSendVerificationCodeViaSMS = { /*TODO*/ },
        onSendVerificationCodeSuccess = { code ->
            Log.e("CALLED", "BorrowerProfilesScreenRoute: STORE VER CODE")
            verificationCode = code
            storeVerificationCode = true
        },
        onSearchValueChange = { queriedName -> lenderDashboardViewModel.collectVerifiedBorrowerUserProfiles(queriedName) },
        onSearchValueSheetChange = { queriedName -> lenderDashboardViewModel.collectUnVerifiedBorrowerUserProfiles(queriedName) },
        verifiedProfiles = verifiedProfilesFlow,
        unVerifiedProfiles = unVerifiedProfilesFlow,
    )
}