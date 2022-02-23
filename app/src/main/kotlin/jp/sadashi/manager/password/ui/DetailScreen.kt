package jp.sadashi.manager.password.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import jp.sadashi.manager.password.data.Password

@Composable
fun DetailBody(password: Password) {
    Column {
        Text(text = password.name)
        Text(text = password.password)
    }
}