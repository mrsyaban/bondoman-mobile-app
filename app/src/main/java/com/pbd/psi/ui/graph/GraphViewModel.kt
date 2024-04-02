package com.pbd.psi.ui.graph

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbd.psi.repository.GraphRepository
import com.pbd.psi.room.Category
import kotlinx.coroutines.launch

class GraphViewModel(private val repository: GraphRepository) : ViewModel() {
    private val _incomeTotal = MutableLiveData<Int>()
    val incomeTotal: LiveData<Int>
        get() = _incomeTotal

    private val _expenseTotal = MutableLiveData<Int>()
    val expenseTotal: LiveData<Int>
        get() = _expenseTotal

    fun fetchIncomeTotal() {
        viewModelScope.launch {
            _incomeTotal.value = repository.getSumTransaction(Category.INCOME)
            Log.d("GraphViewModel", "fetchIncomeTotal: ${_incomeTotal.value}")
        }
    }

    fun fetchExpenseTotal() {
        viewModelScope.launch {
            _expenseTotal.value = repository.getSumTransaction(Category.EXPENSE)
            Log.d("GraphViewModel", "fetchExpenseTotal: ${_expenseTotal.value}")
        }
    }
}
