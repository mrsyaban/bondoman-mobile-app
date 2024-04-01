package com.pbd.psi.repository

import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity

class ScanRepository(private val database: AppDatabase) {

    val transactionList: LiveData<List<TransactionEntity>> = database.transactionDao().getAllTrans()

    suspend fun addTransaction(transactionEntity: TransactionEntity) {
        database.transactionDao().addTransaction(transactionEntity)
    }
}
