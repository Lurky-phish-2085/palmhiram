package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import android.content.res.Configuration.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun TwoRowButtons(
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = onClickLeft,
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(2.dp, Color.LightGray),
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 56.dp, max = 56.dp)
        ) {
            leftContent()
        }

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedButton(
            onClick = onClickRight,
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(2.dp, Color.LightGray),
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 56.dp, max = 56.dp)
        ) {
            rightContent()
        }
    }
}

@Preview(name = "light", showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TwoRowButtonsPreview() {
    PalmHiramTheme {
        Surface {
            TwoRowButtons(
                onClickLeft = {},
                onClickRight = {},
                leftContent = {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text(text = "Apply loan")
                },
                rightContent = {
                    Icon(
                        imageVector = Icons.Default.ArrowOutward,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text(text = "Pay loan")
                }
            )
        }
    }
}
