package com.example.passwordmanager.feature.lock

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor() : ViewModel() {

    private val _isUnlocked = MutableStateFlow(false)
    val isUnlocked: StateFlow<Boolean> = _isUnlocked.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun onAuthenticationSuccess() {
        _isUnlocked.value = true
        _authError.value = null
    }

    fun onAuthenticationError(errorMessage: String) {
        _authError.value = errorMessage
    }

    fun lock() {
        _isUnlocked.value = false
    }
}
