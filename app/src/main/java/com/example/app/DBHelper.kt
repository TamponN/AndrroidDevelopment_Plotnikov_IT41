package com.example.app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Запрос на создание таблицы
    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${DatabaseContract.NotesEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," + // автоинкремент
                "${DatabaseContract.NotesEntry.COLUMN_NAME_TITLE} TEXT," + // Столбец для заголовка
                "${DatabaseContract.NotesEntry.COLUMN_NAME_DESCRIPTION} TEXT)" // Столбец для описания

    // запрос для удаления таблицы
    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseContract.NotesEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES) // Выполняем запрос на создание таблицы
    }

    // обновление версии базы данных
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // удаляем старую таблицу и создаем новую
        db.execSQL(SQL_DELETE_ENTRIES) // просто и сердито
        onCreate(db)
    }

    // константы
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyNotes.db"
    }
}
