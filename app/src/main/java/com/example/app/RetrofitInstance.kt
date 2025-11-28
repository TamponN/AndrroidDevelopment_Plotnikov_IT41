package com.example.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Синглтон для создания и хранения экземпляра Retrofit
object RetrofitInstance {

    // Базовый URL API
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // экземпляр  будет создан только при первом обращении к нему
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // базовый URL
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Собираем объект
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
