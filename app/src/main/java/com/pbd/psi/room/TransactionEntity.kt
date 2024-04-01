package com.pbd.psi.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "transactionTable")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "amount")
    val amount: Int = 0,

    @ColumnInfo(name = "date")
    val date: Date = Date(),

    @ColumnInfo(name = "location")
    val location: String = "",

    @ColumnInfo(name = "longitude")
    val longitude: Double = 0.0,

    @ColumnInfo(name = "latitude")
    val latitude: Double = 0.0,
)