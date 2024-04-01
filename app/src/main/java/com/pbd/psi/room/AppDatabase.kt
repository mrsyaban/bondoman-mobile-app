package com.pbd.psi.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private  var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            synchronized(this){
                var instance = Instance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    Instance = instance
                }

                return instance
            }
        }
    }
}
