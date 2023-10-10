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

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Firebase.auth
                .useEmulator("localhost", 9099)

            Firebase.firestore
                .useEmulator("localhost", 8080)

            Firebase.firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setSslEnabled(false)
                .build()

            Firebase.functions
                .useEmulator("localhost", 5001)
        }
    }
}