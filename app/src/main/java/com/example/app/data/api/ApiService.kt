package com.example.app.data.api

import com.example.app.data.model.UserDto
import retrofit2.http.GET

/**
 * API интерфейс для сетевых запросов
 * эндпоинты для получения данных
 */
interface ApiService {
    @GET("users")
    // функция для получения списка пользователей в фоне
    suspend fun getUsers(): List<UserDto>
}
