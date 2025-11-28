package com.example.app.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton объект для создания Retrofit instance
 */
object RetrofitBuilder {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val client = OkHttpClient.Builder().build()
    // синглтон для всей апки
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // создаём реализацию
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
