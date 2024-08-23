package com.vavilon.storage.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vavilon.model.CategoryTypes
import com.vavilon.model.TransactionCategories
import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TotalDao
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.dao.UserDao
import com.vavilon.storage.local.entities.Currency
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.TotalBalance
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.storage.local.entities.TransactionCategory
import com.vavilon.storage.local.entities.User
import com.vavilon.storage.local.migration.MIGRATION_1_2
import com.vavilon.storage.local.migration.MIGRATION_2_3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Source::class, Currency::class,
        TotalBalance::class, Transaction::class,
        TransactionCategory::class, User::class],
    exportSchema = true,
    version = 3
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
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, "vavilon_app_db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .addCallback(AppDBCallBack())
                .build()
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
                TransactionCategory(
                    category.getTransactionCategory(),
                    CategoryTypes.DEFAULT.getCategoryType()
                )
            }
            defaultCategories.forEach { transactionCategoryDAO.insert(it) }
        }

        suspend fun setDefaultSources(sourceDAO: SourceDao) {
            val demoSources = listOf(
                Source("Income", "Primary Account", "Main banking account", 500.0),
                Source("Income", "Salary", "Salary paid every month", 2000.0),
                Source("Expense", "Cash", "Cash in wallet", 100.0),
                Source("Expense", "Food", "Shopping", 400.0),
                Source("Saving", "Stocks", "Stock market investments", 5000.0)
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
}