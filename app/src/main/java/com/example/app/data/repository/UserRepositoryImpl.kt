package com.example.app.data.repository

import com.example.app.data.api.ApiService
import com.example.app.domain.model.User
import com.example.app.domain.repository.UserRepository

/**
 * Реализация репозитория
 * получение данных из источников
 */
class UserRepositoryImpl(
    private val apiService: ApiService) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            // через сервис ккидаем запрос к апи
            val users = apiService.getUsers()
            // Преобразуем DTO в Domain модели
            val domainUsers = users.map { dto ->
                User(
                    id = dto.id,
                    name = dto.name,
                    username = dto.username,
                    email = dto.email,
                    phone = dto.phone,
                    website = dto.website
                )
            }
            Result.success(domainUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
