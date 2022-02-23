package jp.sadashi.manager.password.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.sadashi.manager.password.data.Password

@Composable
fun ListBody(
    list: List<Password>,
    onItemClick: (Int) -> Unit = {},
) {
    LazyColumn {
        items(list) { item ->
            Text(
                text = item.name,
                modifier = Modifier.clickable {
                    onItemClick(item.id)
                },
            )
        }
    }
}