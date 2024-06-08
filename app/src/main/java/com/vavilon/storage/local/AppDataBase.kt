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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Source::class, Currency::class,
        TotalBalance::class, Transaction::class,
        TransactionCategory::class, User::class],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
                     ],
    version = 2
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
                AppDataBase::class.java, "vavilon_db"
            )
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
                     }
                 }
             }
         }

         suspend fun setDefaultTransactionCategory(transactionCategoryDAO: TransactionCategoryDao) {
             val defaultCategories = TransactionCategories.entries.map { category ->
                 TransactionCategory(category.getTransactionCategory(), CategoryTypes.DEFAULT.getCategoryType())
             }
             defaultCategories.forEach { transactionCategoryDAO.insert(it) }
         }
    }

    abstract fun SourceDao(): SourceDao
    abstract fun TransactionDao(): TransactionDao
    abstract fun TotalDao(): TotalDao
    abstract fun UserDao(): UserDao
    abstract fun CurrencyDao(): CurrencyDao
    abstract fun TransactionCategoryDao(): TransactionCategoryDao
}