package com.pbd.psi.room

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pbd.psi.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "resto_db",
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: AppDatabase) = db.transactionDao()

}