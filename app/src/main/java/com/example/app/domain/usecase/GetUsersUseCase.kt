package com.example.app.domain.usecase

import com.example.app.domain.model.User
import com.example.app.domain.repository.UserRepository

/**
 * Use Case для получения списка пользователей
 */
class GetUsersUseCase(private val _repository: UserRepository) {
    // Обращаемся к репозиторию
    suspend operator fun invoke(): Result<List<User>> {
        return _repository.getUsers()
    }
}
