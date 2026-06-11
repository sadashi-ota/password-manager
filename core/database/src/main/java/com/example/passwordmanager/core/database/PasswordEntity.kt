package com.example.passwordmanager.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceName: String,
    val encryptedUserName: String,
    val encryptedPassword: String,
    val encryptedNote: String,
    val createdAt: Long,
    val updatedAt: Long
)
