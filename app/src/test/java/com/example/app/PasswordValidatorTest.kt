package com.example.app

import org.junit.Assert.*
import org.junit.Test

class PasswordValidatorTest {

    // ест на ваилдный пароль. должен быть true
    @Test
    fun `isPasswordCorrect should return true for a valid password`() {
        // Arrange
        val password = "valid_password"
        // Act
        val validator = PasswordValidator()
        // Assert
        assertTrue(validator.isPasswordCorrect(password))
    }

    // тест на пустой пароль. Должен быть false
    @Test
    fun `isPasswordCorrect should return false for an empty password`() {
        // Arrange
        val password = ""
        // Act
        val validator = PasswordValidator()
        // Assert
        assertFalse(validator.isPasswordCorrect(password))
    }

    // тест на пароль недостаточной длины. Должен быть false
    @Test
    fun `isPasswordCorrect should return false for a password with less than 6 characters`() {
        // Arrange
        val password = "short"
        // act
        val validator = PasswordValidator()
        // Assert
        assertFalse(validator.isPasswordCorrect(password))
    }
}