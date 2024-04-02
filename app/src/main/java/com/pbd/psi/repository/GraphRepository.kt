package com.pbd.psi.repository
import androidx.lifecycle.LiveData
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GraphRepository @Inject constructor(private val database: AppDatabase) {
    suspend fun getSumTransaction(category: Category): Int {
        return withContext(Dispatchers.IO) {
            // Perform database operation here
            // For example:
            database.transactionDao().getSumTransaction(category)
        }
    }

}