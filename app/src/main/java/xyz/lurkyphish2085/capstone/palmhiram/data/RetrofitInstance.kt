package xyz.lurkyphish2085.capstone.palmhiram.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: PalmhiramAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://psychic-enigma-449r967g6wcgrv-4000.app.github.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PalmhiramAPI::class.java)
    }
}