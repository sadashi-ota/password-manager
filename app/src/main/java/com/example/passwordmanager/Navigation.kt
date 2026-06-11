package com.example.passwordmanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.passwordmanager.core.common.CsvParser
import com.example.passwordmanager.feature.lock.LockScreen
import com.example.passwordmanager.feature.lock.LockViewModel
import com.example.passwordmanager.feature.password_list.PasswordListScreen
import com.example.passwordmanager.feature.password_list.PasswordListViewModel
import com.example.passwordmanager.feature.password_detail.PasswordDetailScreen
import com.example.passwordmanager.feature.password_detail.PasswordDetailViewModel
import com.example.passwordmanager.feature.password_edit.PasswordEditScreen
import com.example.passwordmanager.feature.password_edit.PasswordEditViewModel

@Composable
fun MainNavigation(
    lockViewModel: LockViewModel = hiltViewModel()
) {
    val isUnlocked by lockViewModel.isUnlocked.collectAsState()

    if (!isUnlocked) {
        LockScreen(
            viewModel = lockViewModel,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        val backStack = rememberNavBackStack(PasswordList)
        val csvParser = remember { CsvParser() }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<PasswordList> {
                    val viewModel: PasswordListViewModel = hiltViewModel()
                    PasswordListScreen(
                        viewModel = viewModel,
                        csvParser = csvParser,
                        onPasswordClick = { id -> backStack.add(PasswordDetail(id)) },
                        onAddClick = { backStack.add(PasswordEdit()) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                entry<PasswordDetail> { key ->
                    val viewModel: PasswordDetailViewModel = hiltViewModel()
                    PasswordDetailScreen(
                        viewModel = viewModel,
                        onEditClick = { id -> backStack.add(PasswordEdit(id)) },
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                entry<PasswordEdit> { key ->
                    val viewModel: PasswordEditViewModel = hiltViewModel()
                    PasswordEditScreen(
                        viewModel = viewModel,
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        )
    }
}
