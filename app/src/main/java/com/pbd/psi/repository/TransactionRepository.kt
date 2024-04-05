package com.pbd.psi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val database: AppDatabase) {

    val transactionList: LiveData<List<TransactionEntity>> = database.transactionDao().getAllTrans()

    fun getTransById(id: Int):LiveData<TransactionEntity> {
        return database.transactionDao().getTransById(id)
    }

    suspend fun addTransaction(transaction: TransactionEntity) {
       database.transactionDao().addTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: TransactionEntity) {
        database.transactionDao().updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        database.transactionDao().deleteTransaction(transaction)
    }
}