package com.example.app.domain.model

/**
 * Domain модель пользователя
 */
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)
