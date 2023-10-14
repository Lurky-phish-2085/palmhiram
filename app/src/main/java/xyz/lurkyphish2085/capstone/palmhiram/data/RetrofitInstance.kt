package xyz.lurkyphish2085.capstone.palmhiram.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: PalmhiramAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://159.138.82.138:4000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PalmhiramAPI::class.java)
    }
}