package jp.sadashi.manager.password.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.sadashi.manager.password.data.PasswordRecord
import jp.sadashi.manager.password.data.TestData

@ExperimentalMaterialApi
@Composable
fun ListBody(
    records: List<PasswordRecord>,
    onItemClick: (Int) -> Unit = {},
) {
    LazyColumn {
        items(records) { record ->
            ListItem(
                modifier = Modifier
                    .clickable { onItemClick(record.id) }
                    .fillMaxWidth()
                    .padding(8.dp),
                text = { Text(text = record.name) },
                secondaryText = { Text(text = record.note) },
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ListPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        ListBody(records = TestData.PASSWORD_RECORDS) {
        }
    }
}


