package com.example.app

class PasswordValidator {
    // Заглушка функции для написания Unit-теста
    fun isPasswordCorrect(password: String): Boolean {

        return password.isNotBlank() && password.length >= 6

    }

}