package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun TwoRowButtonsWithIcon(
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    TwoRowButtons(
        onClickLeft = onClickLeft,
        onClickRight = onClickRight,
        leftContent = {
            Icon(
                imageVector = leftIcon,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            leftContent()
        },
        rightContent = {
            Icon(
                imageVector = rightIcon,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            rightContent()
        }
    )
}


@Preview(name = "light", showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TwoRowButtonsWithIconPreview() {
    PalmHiramTheme {
        Surface {
            TwoRowButtonsWithIcon(
                onClickLeft = { /*TODO*/ },
                onClickRight = { /*TODO*/ },
                leftIcon = Icons.Default.ArrowDownward,
                rightIcon = Icons.Default.ArrowOutward,
                leftContent = {
                    Text(text = "Apply Loan")
                },
                rightContent = {
                    Text(text = "Pay Loan")
                }
            )
        }
    }
}
