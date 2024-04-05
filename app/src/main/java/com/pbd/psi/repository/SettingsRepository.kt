package com.pbd.psi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val database: AppDatabase) {
    val transactionList: LiveData<List<TransactionEntity>> = database.transactionDao().getAllTrans()

    suspend fun getAllTrans(): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            Log.d("button_export", "Repo1")
            Log.d("ResponseString", "Response: ${database.transactionDao().getAllTransactions()}")
            database.transactionDao().getAllTransactions()
        }
    }
}
