package com.vavilon.di.modules

import com.vavilon.model.dataSource.ExchangeRatesLocalDataSource
import com.vavilon.model.dataSource.impl.ExchangeRatesLocalDataSourceImpl
import com.vavilon.model.repositories.ExchangeRateRepository
import com.vavilon.model.repositories.SourceRepository
import com.vavilon.model.repositories.TransactionRepository
import com.vavilon.services.ExchangeRatesService
import com.vavilon.services.impl.ExchangeRatesServiceImpl
import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.utils.RetrofitInstance
import com.vavilon.viewModel.ExchangeRatesViewModel
import com.vavilon.viewModel.SourceViewModel
import com.vavilon.viewModel.TransactionViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FinanceModule {
    @Singleton
    @Provides
    fun provideSourceRepository(sourceDao: SourceDao): SourceRepository {
        return SourceRepository(sourceDao)
    }

    @Singleton
    @Provides
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
        transactionCategoryDao: TransactionCategoryDao
    ): TransactionRepository {
        return TransactionRepository(transactionDao, transactionCategoryDao)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): RetrofitInstance {
        return RetrofitInstance()
    }

    @Singleton
    @Provides
    fun provideExchangeRatesRepository(currencyDao: CurrencyDao): ExchangeRateRepository {
        return ExchangeRateRepository(currencyDao)
    }

    @Singleton
    @Provides
    fun provideExchangeRatesLocalDataSource(exchangeRateRepository: ExchangeRateRepository): ExchangeRatesLocalDataSource {
        return ExchangeRatesLocalDataSourceImpl(exchangeRateRepository)
    }

    @Singleton
    @Provides
    fun provideExchangeRatesService(
        exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
        retrofitInstance: RetrofitInstance
    ): ExchangeRatesService {
        return ExchangeRatesServiceImpl(exchangeRatesLocalDataSource, retrofitInstance)
    }


    @Singleton
    @Provides
    fun provideSourceViewModel(sourceRepository: SourceRepository): SourceViewModel {
        return SourceViewModel(sourceRepository)
    }

    @Singleton
    @Provides
    fun provideTransactionViewModel(transactionRepository: TransactionRepository): TransactionViewModel {
        return TransactionViewModel(transactionRepository)
    }

    @Singleton
    @Provides
    fun provideExchangeRatesViewModel(
        exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
        exchangeRatesService: ExchangeRatesService
    ): ExchangeRatesViewModel {
        return ExchangeRatesViewModel(exchangeRatesLocalDataSource, exchangeRatesService)
    }
}