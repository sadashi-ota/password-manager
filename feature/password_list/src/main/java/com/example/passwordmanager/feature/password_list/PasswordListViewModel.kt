package com.example.passwordmanager.feature.password_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.core.model.PasswordEntry
import com.example.passwordmanager.domain.usecase.GetPasswordsUseCase
import com.example.passwordmanager.domain.usecase.ImportCsvUseCase
import com.example.passwordmanager.domain.usecase.SearchPasswordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortType {
    SERVICE_NAME, CREATED_AT
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase,
    private val importCsvUseCase: ImportCsvUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.CREATED_AT)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()

    private val _importResult = MutableStateFlow<ImportResult?>(null)
    val importResult: StateFlow<ImportResult?> = _importResult.asStateFlow()

    val passwords: StateFlow<List<PasswordEntry>> = combine(
        _searchQuery.flatMapLatest { query ->
            if (query.isEmpty()) {
                getPasswordsUseCase()
            } else {
                searchPasswordsUseCase(query)
            }
        },
        _sortType
    ) { list, sortType ->
        when (sortType) {
            SortType.SERVICE_NAME -> list.sortedBy { it.serviceName }
            SortType.CREATED_AT -> list.sortedByDescending { it.createdAt }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSortTypeChanged(sortType: SortType) {
        _sortType.value = sortType
    }

    fun importPasswords(entries: List<PasswordEntry>) {
        viewModelScope.launch {
            try {
                importCsvUseCase(entries)
                _importResult.value = ImportResult.Success(entries.size)
            } catch (e: Exception) {
                _importResult.value = ImportResult.Error(e.message ?: "インポートに失敗しました")
            }
        }
    }

    fun clearImportResult() {
        _importResult.value = null
    }
}

sealed class ImportResult {
    data class Success(val count: Int) : ImportResult()
    data class Error(val message: String) : ImportResult()
}
