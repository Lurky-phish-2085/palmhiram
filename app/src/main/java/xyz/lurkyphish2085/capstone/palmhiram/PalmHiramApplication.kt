package xyz.lurkyphish2085.capstone.palmhiram

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import io.grpc.android.BuildConfig

@HiltAndroidApp
class PalmHiramApplication: Application() {
}