package com.vavilon.di.modules

import com.vavilon.model.repositories.SourceRepository
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.viewModel.SourceViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FinanceModule {
    @Singleton
    @Provides
    fun provideFinanceRepository(sourceDao:SourceDao) : SourceRepository {
        return SourceRepository(sourceDao)
    }
    @Singleton
    @Provides
    fun provideSourceViewModel(sourceRepository: SourceRepository) : SourceViewModel {
        return SourceViewModel(sourceRepository)
    }
}