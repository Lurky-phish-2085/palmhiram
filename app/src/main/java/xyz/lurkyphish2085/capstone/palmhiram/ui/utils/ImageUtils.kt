package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream


class ImageUtils {

    companion object {

        suspend fun loadBitmapFromUri(context: Context, uri: Uri?): Bitmap? {

            return uri?.let { nonNullUri ->
                val contentResolver: ContentResolver = context.contentResolver

                try {
                    contentResolver.openFileDescriptor(nonNullUri, "r")
                        ?.use { parcelFileDescriptor ->
                            val fileDescriptor = parcelFileDescriptor.fileDescriptor
                            BitmapFactory.decodeFileDescriptor(fileDescriptor)
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }

        fun encodeBitmapToBase64(bitmap: Bitmap): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        fun decodeBase64ToBitmap(base64String: String?): Bitmap? {
            val decodedByteArray: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}