package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.app.data.api.RetrofitBuilder
import com.example.app.data.repository.UserRepositoryImpl
import com.example.app.domain.usecase.GetUsersUseCase
import com.example.app.presentation.users.UserListScreen
import com.example.app.presentation.users.UserListViewModel
import com.example.app.presentation.users.UserListViewModelFactory
import com.example.app.ui.theme.AppTheme

/**
 * Activity для демонстрации MVVM архитектуры
 * Используем Jetpack Compose для UI
 */
class MVVMActivity : ComponentActivity() {

    // инъекция зависимостей
    private val apiService = RetrofitBuilder.apiService
    private val repository = UserRepositoryImpl(apiService)
    private val getUsersUseCase = GetUsersUseCase(repository)
    private val viewModel: UserListViewModel by viewModels {
        UserListViewModelFactory(getUsersUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Устанавливаем Compose UI
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Главный экран с ViewModel
                    UserListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
