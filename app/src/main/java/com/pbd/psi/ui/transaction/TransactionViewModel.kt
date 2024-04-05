package com.pbd.psi.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
) : ViewModel() {
    var transactionList: LiveData<List<TransactionEntity>> = repository.transactionList

    fun getBalance(): Int {
        var totalIncome = 0
        var totalExpense = 0

        for (transaction in transactionList.value ?: emptyList()) {
            if (transaction.category == Category.INCOME) {
                totalIncome += transaction.amount
            } else {
                totalExpense += transaction.amount
            }
        }

        return  totalIncome - totalExpense
    }
}