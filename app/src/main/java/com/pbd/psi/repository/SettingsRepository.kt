package com.pbd.psi.repository

import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val database: AppDatabase) {
    suspend fun getAllTrans():List<TransactionEntity> {
        return database.transactionDao().getAllTransactions()
    }
}
