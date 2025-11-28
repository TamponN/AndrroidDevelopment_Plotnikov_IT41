package com.example.app.presentation.users

import com.example.app.domain.model.User

/**
 * абстрактный класс состояния UI для экрана списка пользователей
 */
sealed class UserListState {
    object Loading : UserListState() // Состояние загрузки
    data class Success(val users: List<User>) : UserListState() // Успех, есть данные для отображения
    data class Error(val message: String) : UserListState()
}
