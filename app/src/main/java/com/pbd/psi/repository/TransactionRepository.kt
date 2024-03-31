package com.pbd.psi.repository

import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity

class TransactionRepository(private val database: AppDatabase) {

    val transactionList: LiveData<List<TransactionEntity>> = database.transactionDao().getAllTrans()

    fun getTransById(id: Int){
        database.transactionDao().getTransById(id)
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