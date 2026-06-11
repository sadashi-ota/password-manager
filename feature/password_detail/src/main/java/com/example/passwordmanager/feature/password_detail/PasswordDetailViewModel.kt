package com.example.passwordmanager.feature.password_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.core.model.PasswordEntry
import com.example.passwordmanager.domain.usecase.DeletePasswordUseCase
import com.example.passwordmanager.domain.usecase.GetPasswordByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailViewModel @Inject constructor(
    private val getPasswordByIdUseCase: GetPasswordByIdUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val passwordId: Long = savedStateHandle.get<Long>("passwordId") ?: 0L

    private val _passwordEntry = MutableStateFlow<PasswordEntry?>(null)
    val passwordEntry: StateFlow<PasswordEntry?> = _passwordEntry.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    init {
        loadPassword()
    }

    fun loadPassword() {
        viewModelScope.launch {
            _passwordEntry.value = getPasswordByIdUseCase(passwordId)
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun deletePassword(onDeleted: () -> Unit) {
        viewModelScope.launch {
            deletePasswordUseCase(passwordId)
            onDeleted()
        }
    }
}
