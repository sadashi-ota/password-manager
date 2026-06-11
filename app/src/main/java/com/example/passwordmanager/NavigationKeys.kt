package com.example.passwordmanager

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object PasswordList : NavKey
@Serializable data class PasswordDetail(val passwordId: Long) : NavKey
@Serializable data class PasswordEdit(val passwordId: Long = 0L) : NavKey
