package com.pbd.psi.room

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun getAllTrans(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE id=:id")
    fun getTransById(id: Int): TransactionEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTransaction(trans: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTransaction(trans: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(trans: TransactionEntity)
}