package jp.sadashi.manager.password.data

import androidx.compose.runtime.Immutable

@Immutable
data class Password(
    val id: Int,
    val name: String,
    val password: String,
)

object TestData {
    val password: List<Password> = listOf(
        Password(1, "Android", "1111"),
        Password(2, "Jetpack Compose", "2222"),
        Password(3, "Password Manager", "3333"),
    )
}