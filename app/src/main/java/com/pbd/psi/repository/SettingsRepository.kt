package com.pbd.psi.repository

import android.util.Log
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val database: AppDatabase) {
    suspend fun getAllTrans(): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            Log.d("button_export", "Repo1")
            database.transactionDao().getAllTransactions()
        }
    }
}
