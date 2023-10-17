package xyz.lurkyphish2085.capstone.palmhiram.data

import retrofit2.Response
import retrofit2.http.GET
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message

interface PalmhiramAPI {

    @GET("/v1/ping")
    suspend fun getPing(): Response<Message>
}