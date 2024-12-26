package com.vavilon.di.modules

import android.content.Context
import com.vavilon.storage.local.AppDataBase
import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TotalDao
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideRoomDB(context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideSourceDao(dataBase: AppDataBase): SourceDao {
        return dataBase.SourceDao();
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(dataBase: AppDataBase): CurrencyDao {
        return dataBase.CurrencyDao()
    }

    @Singleton
    @Provides
    fun provideTotalDao(dataBase: AppDataBase): TotalDao {
        return dataBase.TotalDao()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(dataBase: AppDataBase): TransactionDao {
        return dataBase.TransactionDao()
    }

    @Singleton
    @Provides
    fun provideTransactionCategoryDao(dataBase: AppDataBase): TransactionCategoryDao {
        return dataBase.TransactionCategoryDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.UserDao()
    }

    @Singleton
    @Provides
    fun providePlanDao(dataBase: AppDataBase): PlanDao {
        return dataBase.PlanDao()
    }
}