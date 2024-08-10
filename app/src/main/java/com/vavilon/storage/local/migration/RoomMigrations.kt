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

        db.execSQL("""
            INSERT INTO transactions_new (transaction_id, amount, description, creation_date, isRepeatable, currency_id, source_id, schedule_id, category_name)
            SELECT transaction_id, amount, description, creation_date, isRepeatable, currency_id, source_id, schedule_id, category_id FROM transactions
        """.trimIndent())

        db.execSQL("DROP TABLE transactions")

        db.execSQL("ALTER TABLE transactions_new RENAME TO transactions")
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Создаем временную таблицу для 'sources' с новой схемой
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS sources_new (
                source_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                type TEXT NOT NULL,
                title TEXT NOT NULL,
                icon_id INTEGER,
                description TEXT NOT NULL,
                current_balance REAL NOT NULL,
                total_balance REAL NOT NULL,
                currency_id INTEGER NOT NULL,
                isRepeatable INTEGER NOT NULL,
                creation_date INTEGER NOT NULL DEFAULT 0,  -- Значение по умолчанию
                schedule_id INTEGER NOT NULL,
                isDeleted INTEGER NOT NULL
            )
        """.trimIndent())

        // Перенос данных из старой таблицы в новую для 'sources'
        db.execSQL("""
            INSERT INTO sources_new (source_id, type, title, icon_id, description, current_balance, total_balance, currency_id, isRepeatable, creation_date, schedule_id, isDeleted)
            SELECT source_id, type, title, icon_id, description, current_balance, total_balance, currency_id, isRepeatable, 
                COALESCE(creation_date, 0),  -- Устанавливаем значение по умолчанию, если creation_date NULL
                schedule_id, isDeleted
            FROM sources
        """.trimIndent())

        // Удаляем старую таблицу 'sources'
        db.execSQL("DROP TABLE sources")

        // Переименовываем новую таблицу 'sources'
        db.execSQL("ALTER TABLE sources_new RENAME TO sources")

        // Создаем временную таблицу для 'transactions' с новой схемой
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS transactions_new (
                transaction_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                amount REAL NOT NULL,
                description TEXT NOT NULL DEFAULT '',
                creation_date INTEGER NOT NULL DEFAULT 0,  -- Значение по умолчанию
                isRepeatable INTEGER NOT NULL,
                currency_id INTEGER NOT NULL,
                source_id INTEGER NOT NULL,
                schedule_id INTEGER NOT NULL,
                category_name TEXT NOT NULL
            )
        """.trimIndent())

        // Перенос данных из старой таблицы в новую для 'transactions'
        db.execSQL("""
            INSERT INTO transactions_new (transaction_id, amount, description, creation_date, isRepeatable, currency_id, source_id, schedule_id, category_name)
            SELECT transaction_id, amount, description, 
                COALESCE(creation_date, 0),  -- Устанавливаем значение по умолчанию, если creation_date NULL
                isRepeatable, currency_id, source_id, schedule_id, category_name
            FROM transactions
        """.trimIndent())

        // Удаляем старую таблицу 'transactions'
        db.execSQL("DROP TABLE transactions")

        // Переименовываем новую таблицу 'transactions'
        db.execSQL("ALTER TABLE transactions_new RENAME TO transactions")
    }
}