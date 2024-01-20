package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.PhonelinkErase
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun ConfirmationDialog(
    isOpen: Boolean,
    positiveButtonOnly: Boolean = false,
    onPositiveClick:() -> Unit,
    onNegativeClick:() -> Unit,
    onDismissRequest: () -> Unit,
    onClose: () -> Unit,
    title: String,
    icon: ImageVector,
    headline: String,
    description: String,
    positiveButtonText: String,
    negativeButtonText: String,
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
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
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
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                    )

                    Text(
                        text = headline,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Button(
                            onClick = onPositiveClick,
                            shape = MaterialTheme.shapes.extraSmall,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = positiveButtonText,
                            )
                        }

                        if (!positiveButtonOnly) {
                            Button(
                                onClick = onNegativeClick,
                                shape = MaterialTheme.shapes.extraSmall,
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = negativeButtonText,
                                )
                            }
                        }
                    }
                }
            }
        }
}

@Preview(name = "light - Borrower", showBackground = true, uiMode = UI_MODE_NIGHT_NO, heightDp = 512, widthDp = 512)
@Composable
fun ConfirmationDialogPreview() {
    PalmHiramTheme {
        Surface {
            ConfirmationDialog(
                isOpen = true,
                positiveButtonOnly = false,
                onPositiveClick = { /*TODO*/ },
                onNegativeClick = { /*TODO*/ },
                onDismissRequest = { /*TODO*/ },
                onClose = { /*TODO*/ },
                title = "Warning",
                icon = Icons.Default.DeleteOutline,
                headline = "Do you wish to delete?",
                description = "This action cannot be undone.",
                positiveButtonText = "YES",
                negativeButtonText = "NO",
            )
        }
    }
}

@Preview(name = "light - Borrower", showBackground = true, uiMode = UI_MODE_NIGHT_NO, heightDp = 512, widthDp = 512)
@Composable
fun ConfirmationDialogPositiveOnlyPreview() {
    PalmHiramTheme {
        Surface {
            ConfirmationDialog(
                isOpen = true,
                positiveButtonOnly = true,
                onPositiveClick = { /*TODO*/ },
                onNegativeClick = { /*TODO*/ },
                onDismissRequest = { /*TODO*/ },
                onClose = { /*TODO*/ },
                title = "Oops!",
                icon = Icons.Outlined.PhonelinkErase,
                headline = "That didn't load right!",
                description = "Please try again later.",
                positiveButtonText = "OKAY",
                negativeButtonText = "OKAY",
            )
        }
    }
}
