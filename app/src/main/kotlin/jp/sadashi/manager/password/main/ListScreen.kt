package jp.sadashi.manager.password.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import jp.sadashi.manager.password.PasswordManagerAppBar
import jp.sadashi.manager.password.PasswordManagerScreen
import jp.sadashi.manager.password.data.PasswordRecord
import jp.sadashi.manager.password.data.TestData
import jp.sadashi.manager.password.theme.PasswordManagerTheme

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navController: NavHostController,
    records: List<PasswordRecord>
) {
    Scaffold(
        topBar = { PasswordManagerAppBar() },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Add, "") },
                text = { Text("Add") },
                onClick = {
                    navController.navigate(PasswordManagerScreen.Detail.name)
                },
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        },
        content = {
            ListBody(records = records, onItemClick = { id ->
                navController.navigate("${PasswordManagerScreen.Detail.name}/${id}")
            })
        }
    )
}

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
                    .fillMaxWidth(),
                text = { Text(text = record.name) },
                secondaryText = { Text(text = record.note) },
            )
        }
    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewList() {
    PasswordManagerTheme {
        val navController = rememberNavController()
        ListScreen(
            navController = navController,
            records = TestData.PASSWORD_RECORDS
        )
    }
}

