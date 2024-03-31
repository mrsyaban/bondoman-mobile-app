package com.pbd.psi.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val category: Int = 0,
    val price: Int = 0,
    val date: Date = Date(),
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
)