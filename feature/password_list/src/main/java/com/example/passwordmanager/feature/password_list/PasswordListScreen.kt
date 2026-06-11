package com.example.passwordmanager.feature.password_list

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.core.common.CsvParseResult
import com.example.passwordmanager.core.common.CsvParser
import com.example.passwordmanager.core.model.PasswordEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordListViewModel,
    csvParser: CsvParser,
    onPasswordClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val passwords by viewModel.passwords.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    val importResult by viewModel.importResult.collectAsState()
    val context = LocalContext.current

    var showFabMenu by remember { mutableStateOf(false) }
    var showImportErrorDialog by remember { mutableStateOf<String?>(null) }
    var showImportSuccessDialog by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }

    val csvLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    if (inputStream != null) {
                        when (val parseResult = csvParser.parse(inputStream)) {
                            is CsvParseResult.Success -> {
                                val now = System.currentTimeMillis()
                                val entries = parseResult.rows.map { row ->
                                    PasswordEntry(
                                        serviceName = row.serviceName,
                                        userName = row.userName,
                                        password = row.password,
                                        note = row.note,
                                        createdAt = now,
                                        updatedAt = now
                                    )
                                }
                                viewModel.importPasswords(entries)
                                showImportSuccessDialog = true
                            }
                            is CsvParseResult.Error -> {
                                showImportErrorDialog = parseResult.message
                            }
                        }
                    }
                } catch (e: Exception) {
                    showImportErrorDialog = "ファイルの読み込みに失敗しました: ${e.message}"
                }
            }
        }
    }

    // Import result handling
    if (importResult is ImportResult.Error) {
        showImportErrorDialog = (importResult as ImportResult.Error).message
        viewModel.clearImportResult()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("パスワード一覧") },
                actions = {
                    Box {
                        TextButton(onClick = { showSortMenu = true }) {
                            Text(
                                when (sortType) {
                                    SortType.SERVICE_NAME -> "サービス名順"
                                    SortType.CREATED_AT -> "作成日時順"
                                }
                            )
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "ソート")
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("サービス名順") },
                                onClick = {
                                    viewModel.onSortTypeChanged(SortType.SERVICE_NAME)
                                    showSortMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("作成日時順") },
                                onClick = {
                                    viewModel.onSortTypeChanged(SortType.CREATED_AT)
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                AnimatedVisibility(visible = showFabMenu) {
                    Column(horizontalAlignment = Alignment.End) {
                        SmallFloatingActionButton(
                            onClick = {
                                showFabMenu = false
                                onAddClick()
                            }
                        ) {
                            Text("追加", modifier = Modifier.padding(horizontal = 12.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        SmallFloatingActionButton(
                            onClick = {
                                showFabMenu = false
                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "text/*"
                                }
                                csvLauncher.launch(intent)
                            }
                        ) {
                            Text("インポート", modifier = Modifier.padding(horizontal = 12.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                FloatingActionButton(
                    onClick = { showFabMenu = !showFabMenu }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "追加")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("サービス名で検索") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "検索") },
                singleLine = true
            )

            if (passwords.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isEmpty()) "パスワードが登録されていません"
                        else "該当するパスワードがありません",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(passwords, key = { it.id }) { entry ->
                        PasswordListItem(
                            entry = entry,
                            onClick = { onPasswordClick(entry.id) }
                        )
                    }
                }
            }
        }
    }

    // Error dialog
    if (showImportErrorDialog != null) {
        AlertDialog(
            onDismissRequest = { showImportErrorDialog = null },
            title = { Text("エラー") },
            text = { Text(showImportErrorDialog ?: "") },
            confirmButton = {
                TextButton(onClick = { showImportErrorDialog = null }) {
                    Text("OK")
                }
            }
        )
    }

    // Success dialog
    if (showImportSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showImportSuccessDialog = false },
            title = { Text("インポート完了") },
            text = { Text("パスワードのインポートが完了しました。") },
            confirmButton = {
                TextButton(onClick = {
                    showImportSuccessDialog = false
                    viewModel.clearImportResult()
                }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun PasswordListItem(
    entry: PasswordEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = entry.serviceName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = entry.userName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                Text(
                    text = dateFormat.format(Date(entry.createdAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
