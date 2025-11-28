package com.example.app

// Необходимые импорты
import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity() {

    // Объявление полей класса
    private lateinit var _recyclerViewNotes: RecyclerView
    private lateinit var _noteAdapter: NoteAdapter
    private lateinit var _dbHelper: DBHelper
    private lateinit var _fabAddNote: FloatingActionButton
    private val _titleForNote = "Заметка"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет
        setContentView(R.layout.activity_list)

        _dbHelper = DBHelper(this)
        _recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        _fabAddNote = findViewById(R.id.fabAddNote)

        _recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        // Инициализируем адаптер с пустым списком и обработчиком кликов
        _noteAdapter = NoteAdapter(emptyList()) { note ->
            // При клике на элемент показываем диалог для обновления/удаления
            showNoteActionDialog(note)
        }
        _recyclerViewNotes.adapter = _noteAdapter

        // Слушатель для кнопки добавления
        _fabAddNote.setOnClickListener {
            showAddNoteDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        // Обновляем список каждый раз, когда Activity становится видимым
        loadNotesFromDb()
    }

    // Метод загрузки данных
    private fun loadNotesFromDb() {
        val db = _dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.NotesEntry.TABLE_NAME,
            null, null, null, null, null, null
        )

        val notes = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NotesEntry.COLUMN_NAME_TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NotesEntry.COLUMN_NAME_DESCRIPTION))
                notes.add(Note(id, title, description))
            }
        }
        cursor.close()

        // Обновляем данные в адаптере
        _noteAdapter.updateData(notes)
    }

    // показ диалога для добавления
    private fun showAddNoteDialog() {
        val editText = EditText(this)
        editText.hint = "Введите описание заметки"

        AlertDialog.Builder(this)
            .setTitle("Новая заметка")
            .setView(editText)
            .setPositiveButton("Добавить") { _, _ ->
                val title = editText.text.toString()
                if (title.isNotBlank()) {
                    addNoteToDb(title)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    // Добавление заметки в базу
    private fun addNoteToDb(title: String) {
        val db = _dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.NotesEntry.COLUMN_NAME_TITLE, _titleForNote)
            put(DatabaseContract.NotesEntry.COLUMN_NAME_DESCRIPTION, title)
        }
        db.insert(DatabaseContract.NotesEntry.TABLE_NAME, null, values)

        // обновляем список
        loadNotesFromDb()
    }

    // Показывает диалог для выбора действия с заметкой
    private fun showNoteActionDialog(note: Note) {
        val options = arrayOf("Редактировать", "Удалить")
        AlertDialog.Builder(this)
            .setTitle("Действие с заметкой")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showUpdateNoteDialog(note) // Редактировать
                    1 -> deleteNoteFromDb(note) // Удалить
                }
            }
            .show()
    }

    // Показывает диалог для редактирования заметки
    private fun showUpdateNoteDialog(note: Note) {
        val editText = EditText(this)
        editText.setText(note.title) // Показываем текущий заголовок

        AlertDialog.Builder(this)
            .setTitle("Редактировать заметку")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newTitle = editText.text.toString()
                if (newTitle.isNotBlank()) {
                    updateNoteInDb(note, newTitle)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    // Обновляет заметку в базе
    private fun updateNoteInDb(note: Note, newTitle: String) {
        val db = _dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.NotesEntry.COLUMN_NAME_TITLE, newTitle)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(note.id.toString())
        db.update(DatabaseContract.NotesEntry.TABLE_NAME, values, selection, selectionArgs)

        loadNotesFromDb() // Обновляем список
    }

    // Удаляет заметку из базы
    private fun deleteNoteFromDb(note: Note) {
        val db = _dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(note.id.toString())
        db.delete(DatabaseContract.NotesEntry.TABLE_NAME, selection, selectionArgs)
        loadNotesFromDb() // Обновляем список
        Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        _dbHelper.close() // закрываем соединение
        super.onDestroy()
    }
}