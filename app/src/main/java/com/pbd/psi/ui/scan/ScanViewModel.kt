package com.pbd.psi.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.ScanRepository
import com.pbd.psi.room.TransactionEntity
import kotlinx.coroutines.launch

class ScanViewModel(private val repository: ScanRepository) : ViewModel() {
    fun addTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transactionEntity)
        }
    }
}
