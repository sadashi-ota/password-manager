package com.example.passwordmanager.feature.password_edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.core.common.PasswordGeneratorConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditScreen(
    viewModel: PasswordEditViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val serviceName by viewModel.serviceName.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val password by viewModel.password.collectAsState()
    val note by viewModel.note.collectAsState()
    val hasUnsavedChanges by viewModel.hasUnsavedChanges.collectAsState()

    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var showGeneratorDialog by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val isNewEntry = viewModel.passwordId == 0L
    val canSave = serviceName.isNotBlank() && password.isNotBlank()

    fun handleBackPress() {
        if (hasUnsavedChanges) {
            showBackConfirmDialog = true
        } else {
            onBack()
        }
    }

    BackHandler {
        handleBackPress()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(if (isNewEntry) "パスワード追加" else "パスワード編集") },
                navigationIcon = {
                    IconButton(onClick = { handleBackPress() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.save(onSuccess = onBack)
                        },
                        enabled = canSave
                    ) {
                        Text("保存", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Service Name
            OutlinedTextField(
                value = serviceName,
                onValueChange = viewModel::onServiceNameChanged,
                label = { Text("サービス名 *") },
                placeholder = { Text("例: Google, GitHub") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // User Name
            OutlinedTextField(
                value = userName,
                onValueChange = viewModel::onUserNameChanged,
                label = { Text("ユーザー名 / メールアドレス") },
                placeholder = { Text("例: user@example.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Password
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = viewModel::onPasswordChanged,
                        label = { Text("パスワード *") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (isPasswordVisible) "パスワードを隠す" else "パスワードを表示"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { showGeneratorDialog = true },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text("生成")
                    }
                }
            }

            // Note
            OutlinedTextField(
                value = note,
                onValueChange = viewModel::onNoteChanged,
                label = { Text("メモ") },
                placeholder = { Text("その他の情報やメモ") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }

    // Confirmation dialog when backing out with unsaved changes
    if (showBackConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showBackConfirmDialog = false },
            title = { Text("変更を破棄しますか？") },
            text = { Text("編集中の内容が保存されていません。変更を破棄して戻りますか？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showBackConfirmDialog = false
                        onBack()
                    }
                ) {
                    Text("OK", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBackConfirmDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }

    // Password generator dialog
    if (showGeneratorDialog) {
        PasswordGeneratorDialog(
            onDismiss = { showGeneratorDialog = false },
            onApply = { generatedPassword ->
                viewModel.onPasswordChanged(generatedPassword)
                showGeneratorDialog = false
            },
            viewModel = viewModel
        )
    }
}

@Composable
private fun PasswordGeneratorDialog(
    onDismiss: () -> Unit,
    onApply: (String) -> Unit,
    viewModel: PasswordEditViewModel
) {
    var length by remember { mutableFloatStateOf(16f) }
    var includeUppercase by remember { mutableStateOf(true) }
    var includeLowercase by remember { mutableStateOf(true) }
    var includeDigits by remember { mutableStateOf(true) }
    var includeSymbols by remember { mutableStateOf(true) }

    var generatedPreview by remember { mutableStateOf("") }

    // Helper to generate preview
    fun generatePreview() {
        val config = PasswordGeneratorConfig(
            length = length.toInt(),
            includeUppercase = includeUppercase,
            includeLowercase = includeLowercase,
            includeDigits = includeDigits,
            includeSymbols = includeSymbols
        )
        // We temporarily generate password using viewModel's injector
        viewModel.generatePassword(config)
    }

    // Listen to changes and regenerate preview
    LaunchedEffect(length, includeUppercase, includeLowercase, includeDigits, includeSymbols) {
        generatePreview()
    }

    val currentPasswordState by viewModel.password.collectAsState()
    generatedPreview = currentPasswordState

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("パスワード自動生成") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Preview Area
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = generatedPreview,
                            style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { generatePreview() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "再生成")
                        }
                    }
                }

                // Length slider
                Column {
                    Text("文字数: ${length.toInt()}")
                    Slider(
                        value = length,
                        onValueChange = { length = it },
                        valueRange = 8f..32f,
                        steps = 23
                    )
                }

                // Checkboxes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CheckboxRow(
                        label = "英大文字 (A-Z)",
                        checked = includeUppercase,
                        onCheckedChange = {
                            if (it || includeLowercase || includeDigits || includeSymbols) {
                                includeUppercase = it
                            }
                        }
                    )
                    CheckboxRow(
                        label = "英小文字 (a-z)",
                        checked = includeLowercase,
                        onCheckedChange = {
                            if (includeUppercase || it || includeDigits || includeSymbols) {
                                includeLowercase = it
                            }
                        }
                    )
                    CheckboxRow(
                        label = "数字 (0-9)",
                        checked = includeDigits,
                        onCheckedChange = {
                            if (includeUppercase || includeLowercase || it || includeSymbols) {
                                includeDigits = it
                            }
                        }
                    )
                    CheckboxRow(
                        label = "記号 (!@#...)",
                        checked = includeSymbols,
                        onCheckedChange = {
                            if (includeUppercase || includeLowercase || includeDigits || it) {
                                includeSymbols = it
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onApply(generatedPreview) },
                enabled = generatedPreview.isNotEmpty()
            ) {
                Text("適用")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}

@Composable
private fun CheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // Handled by Row toggleable
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
