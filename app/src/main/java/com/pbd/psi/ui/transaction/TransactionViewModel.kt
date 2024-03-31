package com.pbd.psi.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.TransactionEntity
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: TransactionRepository,
) : ViewModel() {
    var transactionList: LiveData<List<TransactionEntity>> = repository.transactionList

    fun getTransById(id: Int) = viewModelScope.launch {
        repository.getTransById(id)
    }

    fun addTransaction(trans: TransactionEntity) = viewModelScope.launch {
        repository.addTransaction(trans)
    }

    fun updateTransaction(trans: TransactionEntity) = viewModelScope.launch {
        repository.updateTransaction(trans)
    }

    fun deleteTransaction(trans: TransactionEntity) = viewModelScope.launch {
        repository.deleteTransaction(trans)
    }

    companion object {

    }
}