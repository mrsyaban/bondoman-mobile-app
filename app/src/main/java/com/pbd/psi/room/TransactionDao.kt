package com.pbd.psi.room

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun getAllTrans(): LiveData<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    suspend fun insertTransaction(trans: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.FAIL)
    suspend fun updateTransaction(trans: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(trans: TransactionEntity)
}