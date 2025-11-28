package com.example.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object для пользователя из API
 * Это модель уровня Data Layer
 */
data class UserDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("phone")
    val phone: String,
    
    @SerializedName("website")
    val website: String
)