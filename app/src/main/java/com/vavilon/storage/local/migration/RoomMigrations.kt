package com.vavilon.storage.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS transactions_new (
                transaction_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                amount REAL NOT NULL,
                description TEXT NOT NULL DEFAULT '',
                creation_date INTEGER,
                isRepeatable INTEGER NOT NULL,
                currency_id INTEGER NOT NULL,
                source_id INTEGER NOT NULL,
                schedule_id INTEGER NOT NULL,
                category_name TEXT NOT NULL
            )
        """.trimIndent())

        // Копирование данных из старой таблицы в новую таблицу
        db.execSQL("""
            INSERT INTO transactions_new (transaction_id, amount, description, creation_date, isRepeatable, currency_id, source_id, schedule_id, category_name)
            SELECT transaction_id, amount, description, creation_date, isRepeatable, currency_id, source_id, schedule_id, category_id FROM transactions
        """.trimIndent())

        // Удаление старой таблицы
        db.execSQL("DROP TABLE transactions")

        // Переименование новой таблицы в оригинальное имя таблицы
        db.execSQL("ALTER TABLE transactions_new RENAME TO transactions")
    }
}