package com.example.passwordmanager.feature.password_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.core.common.PasswordGenerator
import com.example.passwordmanager.core.common.PasswordGeneratorConfig
import com.example.passwordmanager.core.model.PasswordEntry
import com.example.passwordmanager.domain.usecase.GetPasswordByIdUseCase
import com.example.passwordmanager.domain.usecase.SavePasswordUseCase
import com.example.passwordmanager.domain.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordEditViewModel @Inject constructor(
    private val getPasswordByIdUseCase: GetPasswordByIdUseCase,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val passwordGenerator: PasswordGenerator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val passwordId: Long = savedStateHandle.get<Long>("passwordId") ?: 0L

    private val _serviceName = MutableStateFlow("")
    val serviceName: StateFlow<String> = _serviceName.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()

    private val _initialServiceName = MutableStateFlow("")
    private val _initialUserName = MutableStateFlow("")
    private val _initialPassword = MutableStateFlow("")
    private val _initialNote = MutableStateFlow("")

    val hasUnsavedChanges: StateFlow<Boolean> = combine(
        _serviceName, _userName, _password, _note,
        _initialServiceName, _initialUserName, _initialPassword, _initialNote
    ) { flowsArray ->
        val currentService = flowsArray[0]
        val currentUsername = flowsArray[1]
        val currentPassword = flowsArray[2]
        val currentNote = flowsArray[3]
        val initService = flowsArray[4]
        val initUsername = flowsArray[5]
        val initPassword = flowsArray[6]
        val initNote = flowsArray[7]

        currentService != initService ||
                currentUsername != initUsername ||
                currentPassword != initPassword ||
                currentNote != initNote
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        if (passwordId > 0L) {
            loadPassword()
        }
    }

    private fun loadPassword() {
        viewModelScope.launch {
            val entry = getPasswordByIdUseCase(passwordId)
            if (entry != null) {
                _serviceName.value = entry.serviceName
                _userName.value = entry.userName
                _password.value = entry.password
                _note.value = entry.note

                _initialServiceName.value = entry.serviceName
                _initialUserName.value = entry.userName
                _initialPassword.value = entry.password
                _initialNote.value = entry.note
            }
        }
    }

    fun onServiceNameChanged(value: String) {
        _serviceName.value = value
    }

    fun onUserNameChanged(value: String) {
        _userName.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun onNoteChanged(value: String) {
        _note.value = value
    }

    fun generatePassword(config: PasswordGeneratorConfig) {
        _password.value = passwordGenerator.generate(config)
    }

    fun save(onSuccess: () -> Unit) {
        if (_serviceName.value.isBlank() || _password.value.isBlank()) {
            return
        }
        viewModelScope.launch {
            val entry = PasswordEntry(
                id = passwordId,
                serviceName = _serviceName.value.trim(),
                userName = _userName.value.trim(),
                password = _password.value,
                note = _note.value.trim(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            if (passwordId > 0L) {
                updatePasswordUseCase(entry)
            } else {
                savePasswordUseCase(entry)
            }
            onSuccess()
        }
    }
}
