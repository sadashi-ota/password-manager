package jp.sadashi.manager.password.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import jp.sadashi.manager.password.PasswordManagerAppBar
import jp.sadashi.manager.password.theme.PasswordManagerTheme

@Composable
fun DetailScreen(
    navController: NavHostController,
    record: PasswordRecord = PasswordRecord.EMPTY
) {
    Scaffold(
        topBar = {
            PasswordManagerAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.Done, contentDescription = "Done")
                    }
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = { DetailBody(record) },
    )
}

@Composable
fun DetailBody(record: PasswordRecord) {
    var name: String by remember { mutableStateOf(record.name) }
    var note: String by remember { mutableStateOf(record.note) }
    var password: String by rememberSaveable { mutableStateOf(record.password) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { name = it },
            label = { Text(text = "Name") },
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        OutlinedTextField(
            value = note,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { note = it },
            label = { Text(text = "Note") },
        )
    }
}

@Preview
@Composable
fun PreviewDetail() {
    PasswordManagerTheme {
        val navController = rememberNavController()
        DetailScreen(
            navController = navController,
            record = PasswordRecord(
                id = 0,
                name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                password = "1111",
                note = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
            ),
        )
    }
}