package com.pbd.psi.ui.transaction_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    fun updateTransaction(trans: TransactionEntity) = viewModelScope.launch {
        repository.updateTransaction(trans)
    }

    fun getTransById(id: Int): LiveData<TransactionEntity> {
        return repository.getTransById(id)
    }

    fun deleteTransaction(trans: TransactionEntity) = viewModelScope.launch {
        repository.deleteTransaction(trans)
    }
}