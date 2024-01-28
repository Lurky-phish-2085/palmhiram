package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.ImageUtils
import javax.inject.Inject

@HiltViewModel
class BorrowerConfirmLoanPaymentViewModel @Inject constructor(

): ViewModel() {

    var selectedImageUri: MutableStateFlow<Uri?>? = MutableStateFlow(null)
    var selectedBitmap: MutableStateFlow<ImageBitmap?>? = MutableStateFlow(null)
    var bitmapBase64String: MutableStateFlow<String> = MutableStateFlow("")

    var remarks: MutableStateFlow<String> = MutableStateFlow("")
    var areAllFieldsOkay: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun updateImageBox(context: Context, uri: Uri?) = viewModelScope.launch {
        selectedImageUri?.value = uri
        selectedBitmap?.value = ImageUtils.loadBitmapFromUri(context, uri)?.asImageBitmap()

        bitmapBase64String?.value = ImageUtils.encodeBitmapToBase64(selectedBitmap?.value?.asAndroidBitmap()!!)!!

        checkFieldsValidity()
    }

    fun updateRemarks(input: String) {
        remarks.value = input

        checkFieldsValidity()
    }

    fun checkFieldsValidity() {
        areAllFieldsOkay.value =
            selectedImageUri?.value != null &&
            selectedBitmap?.value != null &&
            (remarks?.value?.length in 1..257)
    }
}