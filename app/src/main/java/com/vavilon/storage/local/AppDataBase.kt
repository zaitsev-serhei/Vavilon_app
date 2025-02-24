package com.vavilon.storage.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vavilon.model.CategoryTypes
import com.vavilon.model.TransactionCategories
import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TotalDao
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.dao.UserDao
import com.vavilon.storage.local.entities.CurrencyEntity
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.SourceForPlan
import com.vavilon.storage.local.entities.TotalBalance
import com.vavilon.storage.local.entities.TransactionEntity
import com.vavilon.storage.local.entities.TransactionCategoryEntity
import com.vavilon.storage.local.entities.TransactionForPlan
import com.vavilon.storage.local.entities.TransactionForSource
import com.vavilon.storage.local.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        SourceEntity::class, CurrencyEntity::class,
        TotalBalance::class, TransactionEntity::class,
        TransactionCategoryEntity::class, UserEntity::class,
        PlanEntity::class, TransactionForPlan::class,
        TransactionForSource::class, SourceForPlan::class],
    exportSchema = true,
    version = 1
)
@TypeConverters(value = [Converter::class])
abstract class AppDataBase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDB(context).also { instance = it }
            }
        }

        private fun buildDB(context: Context): AppDataBase {
            deleteDatabaseFile(context, "vavilon_app_db")
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, "vavilon_app_db"
            )
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)

                .fallbackToDestructiveMigration()
                .addCallback(AppDBCallBack())
                .build()
        }
        private fun deleteDatabaseFile(context: Context, databaseName: String) {
            context.getDatabasePath(databaseName)?.let { dbFile ->
                if (dbFile.exists()) {
                    dbFile.delete()
                }
            }
        }

        private class AppDBCallBack : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { dataBase ->
                    CoroutineScope(Dispatchers.IO).launch {
                        setDefaultTransactionCategory(dataBase.TransactionCategoryDao())
                        setDefaultSources(dataBase.SourceDao())
                    }
                }
            }
        }

        suspend fun setDefaultTransactionCategory(transactionCategoryDAO: TransactionCategoryDao) {
            val defaultCategories = TransactionCategories.entries.map { category ->
                TransactionCategoryEntity(
                    category.getTransactionCategory(),
                    CategoryTypes.DEFAULT.getCategoryType()
                )
            }
            defaultCategories.forEach { transactionCategoryDAO.insert(it) }
        }

        suspend fun setDefaultSources(sourceDAO: SourceDao) {
            val demoSources = listOf(
                SourceEntity("Income", "Primary Account", "Main banking account", 500.0),
                SourceEntity("Income", "Salary", "Salary paid every month", 2000.0),
                SourceEntity("Saving", "Stocks", "Stock market investments", 5000.0)
            )
            demoSources.forEach { sourceDAO.insert(it) }
        }
    }

    abstract fun SourceDao(): SourceDao
    abstract fun TransactionDao(): TransactionDao
    abstract fun TotalDao(): TotalDao
    abstract fun UserDao(): UserDao
    abstract fun CurrencyDao(): CurrencyDao
    abstract fun TransactionCategoryDao(): TransactionCategoryDao
    abstract fun PlanDao(): PlanDao
}