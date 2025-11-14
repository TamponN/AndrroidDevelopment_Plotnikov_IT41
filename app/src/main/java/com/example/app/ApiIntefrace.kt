package com.example.app

import retrofit2.Call
import retrofit2.http.GET

// Интерфейс для описания запросов к API
interface ApiService {
    // у jsonplaceholder один эндпоинт posts
    @GET("posts")

    // метод Get-запроса
    fun getPosts(): Call<List<Post>>
}
