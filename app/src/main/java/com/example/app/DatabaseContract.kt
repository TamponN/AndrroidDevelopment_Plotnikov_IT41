package com.example.app

// импорт для поля id по умолчанию
import android.provider.BaseColumns

// описание структуру базы данных
object DatabaseContract {

    // наследуемся от класса базовых колонок
    object NotesEntry : BaseColumns {
        const val TABLE_NAME = "notes" // имя таблицы
        const val COLUMN_NAME_TITLE = "title" // первая колонка
        const val COLUMN_NAME_DESCRIPTION = "description" // вторая колонка
    }

}
