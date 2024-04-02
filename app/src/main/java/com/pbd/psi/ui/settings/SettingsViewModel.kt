package com.pbd.psi.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.SettingsRepository
import com.pbd.psi.room.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {
    var transactionList: List<TransactionEntity> = emptyList()

    suspend fun fetchAllTransactions(): List<TransactionEntity> {
        val deferred = CompletableDeferred<List<TransactionEntity>>()
        viewModelScope.launch {
            val transactions = repository.getAllTrans()
            deferred.complete(transactions)
        }
        return deferred.await()
    }
}

