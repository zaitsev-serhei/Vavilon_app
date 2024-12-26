package com.vavilon.di.modules

import com.vavilon.model.repositories.PlanRepository
import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlanModule {
    @Singleton
    @Provides
    fun providePlanRepository(
        planDao: PlanDao,
        transactionDao: TransactionDao,
        sourceDao: SourceDao
    ): PlanRepository {
        return PlanRepository(planDao,transactionDao, sourceDao)
    }
}