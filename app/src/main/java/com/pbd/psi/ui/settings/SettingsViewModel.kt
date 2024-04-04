package com.pbd.psi.ui.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.SettingsRepository
import com.pbd.psi.room.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {
    var transactionList: List<TransactionEntity> = emptyList()

    suspend fun fetchAllTransactions(): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            Log.d("button_export", "Repo1")
            repository.getAllTrans()
        }
    }
}

