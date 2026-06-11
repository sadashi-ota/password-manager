package com.example.passwordmanager.core.model

data class PasswordEntry(
    val id: Long = 0,
    val serviceName: String,
    val userName: String,
    val password: String,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long
)
