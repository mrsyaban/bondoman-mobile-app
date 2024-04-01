package com.pbd.psi.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactionTable")
    fun getAllTrans(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactionTable WHERE id=:id")
    fun getTransById(id: Int): TransactionEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTransaction(trans: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTransaction(trans: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(trans: TransactionEntity)
}