package com.example.passwordmanager.data.infra

import com.example.passwordmanager.core.crypto.CryptoManager
import com.example.passwordmanager.core.database.PasswordDao
import com.example.passwordmanager.core.database.PasswordEntity
import com.example.passwordmanager.core.model.PasswordEntry
import com.example.passwordmanager.domain.usecase.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val cryptoManager: CryptoManager
) : PasswordRepository {

    override fun getAllPasswords(): Flow<List<PasswordEntry>> {
        return passwordDao.getAllPasswords().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchPasswords(query: String): Flow<List<PasswordEntry>> {
        return passwordDao.searchPasswords(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getPasswordById(id: Long): PasswordEntry? {
        return passwordDao.getPasswordById(id)?.toDomain()
    }

    override suspend fun savePassword(entry: PasswordEntry): Long {
        val now = System.currentTimeMillis()
        val entity = PasswordEntity(
            serviceName = entry.serviceName,
            encryptedUserName = cryptoManager.encrypt(entry.userName),
            encryptedPassword = cryptoManager.encrypt(entry.password),
            encryptedNote = cryptoManager.encrypt(entry.note),
            createdAt = now,
            updatedAt = now
        )
        return passwordDao.insertPassword(entity)
    }

    override suspend fun updatePassword(entry: PasswordEntry) {
        val existing = passwordDao.getPasswordById(entry.id) ?: return
        val entity = existing.copy(
            serviceName = entry.serviceName,
            encryptedUserName = cryptoManager.encrypt(entry.userName),
            encryptedPassword = cryptoManager.encrypt(entry.password),
            encryptedNote = cryptoManager.encrypt(entry.note),
            updatedAt = System.currentTimeMillis()
        )
        passwordDao.updatePassword(entity)
    }

    override suspend fun deletePassword(id: Long) {
        passwordDao.deletePasswordById(id)
    }

    override suspend fun importPasswords(entries: List<PasswordEntry>) {
        val now = System.currentTimeMillis()
        entries.forEach { entry ->
            val entity = PasswordEntity(
                serviceName = entry.serviceName,
                encryptedUserName = cryptoManager.encrypt(entry.userName),
                encryptedPassword = cryptoManager.encrypt(entry.password),
                encryptedNote = cryptoManager.encrypt(entry.note),
                createdAt = now,
                updatedAt = now
            )
            passwordDao.insertPassword(entity)
        }
    }

    private fun PasswordEntity.toDomain(): PasswordEntry {
        return PasswordEntry(
            id = id,
            serviceName = serviceName,
            userName = cryptoManager.decrypt(encryptedUserName),
            password = cryptoManager.decrypt(encryptedPassword),
            note = cryptoManager.decrypt(encryptedNote),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
