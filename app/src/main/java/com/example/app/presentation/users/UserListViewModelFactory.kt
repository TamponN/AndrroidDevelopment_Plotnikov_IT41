package com.example.app.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.domain.usecase.GetUsersUseCase

/**
 * фабрика для создания ViewModel с зависимостями
 */
class UserListViewModelFactory(private val getUsersUseCase: GetUsersUseCase) : ViewModelProvider.Factory {
    // нужна потому что у нашей вью есть зависимости и мы не можем создать ее стандартным способом
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(getUsersUseCase) as T
        }
        throw IllegalArgumentException("Неизвестный вью")
    }
}
