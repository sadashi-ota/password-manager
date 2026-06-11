package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.core.model.PasswordEntry
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun getAllPasswords(): Flow<List<PasswordEntry>>
    fun searchPasswords(query: String): Flow<List<PasswordEntry>>
    suspend fun getPasswordById(id: Long): PasswordEntry?
    suspend fun savePassword(entry: PasswordEntry): Long
    suspend fun updatePassword(entry: PasswordEntry)
    suspend fun deletePassword(id: Long)
    suspend fun importPasswords(entries: List<PasswordEntry>)
}
