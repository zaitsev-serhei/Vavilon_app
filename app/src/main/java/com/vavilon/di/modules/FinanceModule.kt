package com.vavilon.di.modules

import com.vavilon.model.repositories.SourceRepository
import com.vavilon.model.repositories.TransactionRepository
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.viewModel.SourceViewModel
import com.vavilon.viewModel.TransactionViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FinanceModule {
    @Singleton
    @Provides
    fun provideSourceRepository(sourceDao:SourceDao) : SourceRepository {
        return SourceRepository(sourceDao)
    }
    @Singleton
    @Provides
    fun provideTransactionRepository(transactionDao: TransactionDao, transactionCategoryDao: TransactionCategoryDao) : TransactionRepository {
        return TransactionRepository(transactionDao, transactionCategoryDao)
    }
    @Singleton
    @Provides
    fun provideSourceViewModel(sourceRepository: SourceRepository) : SourceViewModel {
        return SourceViewModel(sourceRepository)
    }
    @Singleton
    @Provides
    fun provideTransactionViewModel(transactionRepository: TransactionRepository) : TransactionViewModel {
        return TransactionViewModel(transactionRepository)
    }
}