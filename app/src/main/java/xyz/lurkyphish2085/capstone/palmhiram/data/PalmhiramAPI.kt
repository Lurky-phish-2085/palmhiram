package xyz.lurkyphish2085.capstone.palmhiram.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.SendEmailRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.SendEmailResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeResponse

interface PalmhiramAPI {

    @GET("/v1/ping")
    suspend fun getPing(): Response<Message>

    @POST("/v1/send-otp")
    suspend fun generateOtp(@Body request: OTPRequest): Response<OTPResponse>

    @POST("/v1/send-verification")
    suspend fun sendVerificationEmail(@Body request: SendEmailRequest): Response<SendEmailResponse>

    @POST("/v1/send-verification-code")
    suspend fun sendVerificationCodeEmail(@Body request: VerificationCodeRequest): Response<VerificationCodeResponse>
}