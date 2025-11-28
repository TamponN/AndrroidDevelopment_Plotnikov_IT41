package com.example.app.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана списка пользователей
 * тут реализовано управление UI состоянием и бизнес-логикой
 */
class UserListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UserListState>(UserListState.Loading)
    val uiState: StateFlow<UserListState> = _uiState.asStateFlow()

    init {
        // Как только ViewModel создается, грузим данные
        loadUsers()
    }

    fun loadUsers() {
        // живёт пока жива вью
        viewModelScope.launch {
            _uiState.value = UserListState.Loading // состояние загрузки

            // Получаем список пользователей из UseCase
            getUsersUseCase().fold(
                onSuccess = { users ->
                    _uiState.value = UserListState.Success(users)
                },
                onFailure = { exception ->
                    _uiState.value = UserListState.Error(
                        exception.message ?: "Неизвестная ошибка"
                    )
                }
            )
        }
    }
}
