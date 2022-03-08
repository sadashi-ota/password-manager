package jp.sadashi.manager.password.data

import androidx.compose.runtime.Immutable

@Immutable
data class PasswordRecord(
    val id: Int,
    val name: String,
    val password: String,
    val note: String,
)

object TestData {
    val PASSWORD_RECORDS: List<PasswordRecord> = listOf(
        PasswordRecord(1, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(2, "Jetpack Compose", "2222", "ノートサンプル2"),
        PasswordRecord(3, "Password Manager", "3333", "ノートサンプル3"),
    )
}