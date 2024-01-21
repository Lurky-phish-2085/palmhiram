package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.splash

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseNetworkException
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ConfirmationDialog
import java.net.UnknownHostException

@ExperimentalMaterial3Api
@Composable
fun SplashRoute(
    viewModel: SplashScreenViewModel,
    onInternetOkay: () -> Unit,
) {
    val context = LocalContext.current

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var isInternetConnectionWarningDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val hasDelayFinishedFlow = viewModel.delayFlow.collectAsState(false)
    val checkInternetFlow = viewModel.internetCheckFlow.collectAsState()

    var startCheckInternet by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(startCheckInternet) {
        if (startCheckInternet) viewModel.checkInternet()
        startCheckInternet = false
    }

//    checkInternetFlow?.value?.let {
//        when(it) {
//            is Resource.Failure -> {
//                LaunchedEffect(Unit) {
//                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
//                        .show()
//                    if (it.exception is UnknownHostException || it.exception is FirebaseNetworkException || it.exception.cause?.message?.contains("java.net.UnknownHostException")!!) {
//                        isInternetConnectionWarningDialogOpen = true
//                        isLoading = false
//                    } else {
//                        onInternetOkay()
//                        isLoading = false
//                    }
//                }
//            }
//            Resource.Loading -> {
//                LaunchedEffect(Unit) {
//                    isLoading = true
//                }
//            }
//            is Resource.Success -> {
//                isLoading = false
//                if (it.result.isEmpty()) {
//                    isInternetConnectionWarningDialogOpen = true
//                } else {
//                    onInternetOkay()
//                }
//            }
//        }
//    }

    ConfirmationDialog(
        isOpen = isInternetConnectionWarningDialogOpen,
        onPositiveClick = {
            val haha: Int? = null
            haha!!.compareTo(69)
        },
        positiveButtonOnly = true,
        onNegativeClick = {},
        onDismissRequest = {},
        onClose = {},
        title = "Network Failure",
        icon = Icons.Default.NetworkCheck,
        headline = "That didn't load right!",
        description = "Please check your internet connection and try again later.",
        positiveButtonText = "OKAY",
        negativeButtonText = "OKAY"
    )

    SplashScreen(
        onDelayDone = onInternetOkay,
        displayLoading = isLoading,
        delayHasFinished = hasDelayFinishedFlow.value,
        modifier = Modifier.padding(16.dp)
    )
}