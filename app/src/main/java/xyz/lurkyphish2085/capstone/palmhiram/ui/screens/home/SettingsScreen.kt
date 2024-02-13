package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel


@Composable
fun SettingsScreen(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    globalAuthViewModel: AuthViewModel,
    modifier: Modifier
) {
    val context = LocalContext.current

    var oldPassword by rememberSaveable {
        mutableStateOf("")
    }
    var newPassword by rememberSaveable {
        mutableStateOf("")
    }

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    CircularProgressLoadingIndicator(isLoading)

    val changePassFlow = globalAuthViewModel.changePasswordFlow.collectAsState()
    changePassFlow.value.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoading = false
                }
            }
            Resource.Loading -> { isLoading = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: ${it.result.uid}:${it.result.displayName}", Toast.LENGTH_SHORT)
                        .show()

                    isLoading = false
                }
            }
            else -> {}
        }
    }

    Column(modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onClose, modifier = Modifier.align(Alignment.BottomStart)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Change Password", style = MaterialTheme.typography.headlineLarge)
        Divider(modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            label = { Text(text = "Old Password") },
            value = oldPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { oldPassword = it }
        )
        OutlinedTextField(
            label = { Text(text = "New Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            value = newPassword,
            onValueChange = { newPassword = it }
        )
        Button( onClick = { globalAuthViewModel.changePassword(oldPassword, newPassword) } ) {
            Text(text = "Apply password")
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}