package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.core.model.PasswordEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPasswordsUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    operator fun invoke(): Flow<List<PasswordEntry>> = repository.getAllPasswords()
}

class SearchPasswordsUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    operator fun invoke(query: String): Flow<List<PasswordEntry>> = repository.searchPasswords(query)
}

class GetPasswordByIdUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(id: Long): PasswordEntry? = repository.getPasswordById(id)
}

class SavePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(entry: PasswordEntry): Long = repository.savePassword(entry)
}

class UpdatePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(entry: PasswordEntry) = repository.updatePassword(entry)
}

class DeletePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(id: Long) = repository.deletePassword(id)
}

class ImportCsvUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(entries: List<PasswordEntry>) = repository.importPasswords(entries)
}
