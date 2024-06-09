package com.vavilon.model.repositories

import com.vavilon.model.SourceCategories
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.entities.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository @Inject constructor(private val sourceDao: SourceDao) {
    private val sourceList = sourceDao.getAllSources()

    fun getAllSourcesList() = sourceList

    suspend fun createSource(source: Source) {
        withContext(Dispatchers.IO) {
            sourceDao.insert(source)
        }
    }

    suspend fun updateSource(source: Source) {
        withContext(Dispatchers.IO) {
            sourceDao.update(source)
        }
    }

    fun getSourceListAsc(category: SourceCategories): Flow<List<Source>> {
        return sourceDao.getSourceListSortedAsc(category.getSrcCategory())
    }

    fun getSourceListDesc(category: SourceCategories): Flow<List<Source>> {
        return sourceDao.getSourceListSortedDesc(category.getSrcCategory())
    }

    fun getSourceListSortedBalanceAsc(category: SourceCategories): Flow<List<Source>> {
        return sourceDao.getSourceListSortedBalance(category.getSrcCategory())
    }

    fun getSourceListCreationDate(category: SourceCategories): Flow<List<Source>> {
        return sourceDao.getSourceListSortedLastAdded(category.getSrcCategory())
    }

    fun getSourceListSortedType(category: SourceCategories): Flow<List<Source>> {
        return sourceDao.getSourceListSortedType(category.getSrcCategory())
    }

    suspend fun deleteSource(source: Source) {
        source.isDeleted = true
        sourceDao.update(source)
    }
}