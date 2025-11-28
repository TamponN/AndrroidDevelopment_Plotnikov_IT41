package com.example.app.domain.repository
import com.example.app.domain.model.User

/**
 * Интерфейс Repository для уровня бизнес-логики
 */
interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}