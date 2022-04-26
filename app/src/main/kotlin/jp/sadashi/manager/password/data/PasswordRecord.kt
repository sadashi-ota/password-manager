package jp.sadashi.manager.password.data

import androidx.compose.runtime.Immutable

@Immutable
data class PasswordRecord(
    val id: Int,
    val name: String,
    val password: String,
    val note: String,
) {
    companion object {
        val EMPTY = PasswordRecord(id = 0, name = "", password = "", note = "")
    }
}

object TestData {
    val PASSWORD_RECORDS: List<PasswordRecord> = listOf(
        PasswordRecord(1, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(2, "Jetpack Compose", "2222", "ノートサンプル2"),
        PasswordRecord(3, "Password Manager", "3333", "ノートサンプル3"),
        PasswordRecord(4, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(5, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(6, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(7, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(8, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(9, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(10, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(11, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(12, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(13, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(14, "Android", "1111", "ノートサンプル1"),
        PasswordRecord(15, "Android", "1111", "ノートサンプル1"),
    )
}