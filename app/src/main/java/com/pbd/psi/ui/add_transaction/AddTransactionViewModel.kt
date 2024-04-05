package com.pbd.psi.ui.add_transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel
    @Inject constructor(
        private val repository: TransactionRepository
    ) : ViewModel() {
    fun addTransaction(transaction:TransactionEntity ) = viewModelScope.launch {
        repository.addTransaction(transaction)
    }
}