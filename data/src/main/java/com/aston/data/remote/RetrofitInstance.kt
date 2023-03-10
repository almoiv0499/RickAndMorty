package com.aston.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private fun retrofitInstance() = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val charactersService: CharactersService = retrofitInstance().create(CharactersService::class.java)

}