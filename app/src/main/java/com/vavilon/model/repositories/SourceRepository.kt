package com.vavilon.model.repositories

import com.vavilon.model.SourceCategory
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.entities.Source
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository @Inject constructor(private val sourceDao: SourceDao) {
    private val sourceList = sourceDao.getAllSources()

    fun getAllSourcesList() = sourceList
    
    suspend fun createSource(source: Source) {
        sourceDao.insert(source)
    }

    suspend fun updateSource(source: Source) {
        sourceDao.update(source)
    }

    fun getSourceListAsc(category: SourceCategory): Flow<List<Source>> {
        return sourceDao.getSourceListSortedAsc(category.getSrcCategory())
    }

    fun getSourceListDesc(category: SourceCategory): Flow<List<Source>> {
        return sourceDao.getSourceListSortedDesc(category.getSrcCategory())
    }

    fun getSourceListSortedBalanceAsc(category: SourceCategory): Flow<List<Source>> {
        return sourceDao.getSourceListSortedBalance(category.getSrcCategory())
    }

    fun getSourceListCreationDate(category: SourceCategory): Flow<List<Source>> {
        return sourceDao.getSourceListSortedLastAdded(category.getSrcCategory())
    }

    fun getSourceListSortedType(category: SourceCategory): Flow<List<Source>> {
        return sourceDao.getSourceListSortedType(category.getSrcCategory())
    }

    suspend fun deleteSource(source: Source) {
        source.isDeleted = true
        sourceDao.update(source)
    }
}