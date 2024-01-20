package xyz.lurkyphish2085.capstone.palmhiram.observeconnectivity

import android.net.http.UrlRequest
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}