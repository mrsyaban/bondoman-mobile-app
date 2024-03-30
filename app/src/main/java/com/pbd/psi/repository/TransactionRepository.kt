package com.pbd.psi.repository

import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity

class TransactionRepository(private val database: AppDatabase) {

    val transactionList: LiveData<List<TransactionEntity>> = database.transactionDao().getAllTrans()

    suspend fun insertTransaction(transaction: TransactionEntity) {
        database.transactionDao().insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: TransactionEntity) {
        database.transactionDao().updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        database.transactionDao().deleteTransaction(transaction)
    }
}