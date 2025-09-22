package com.example.app

// Импорт библиотек для логирования и работы с экранными компонентами
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
// Импорт библы для уведомлений
import android.widget.Toast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    // Объявляем TAG для логирования
    private val TAG = "MainActivityLifecycle"

    // Объявляем переменные для наших View элементов lateinit означает что мы инициализируем их позже
    private lateinit var _editTextLogin: EditText
    private lateinit var _buttonLogin: Button
    private lateinit var _textViewDisplay: TextView
    private lateinit var _buttonOpenList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // включение отображения под системными элементами
        // Ставим свой макет в качестве основного
        setContentView(R.layout.activity_main)
        // Лог вызова метода onCreate
        Log.d(TAG, "onCreate: Activity создается")

        // Инициализируем наши View элементы, находя их по ID из XML-макета
        _editTextLogin = findViewById(R.id.editTextLogin)
        _buttonLogin = findViewById(R.id.buttonLogin)
        _textViewDisplay = findViewById(R.id.textViewDisplay)
        _buttonOpenList = findViewById(R.id.buttonOpenList)

        // Слушатель события на нажатие кнопки
        _buttonLogin.setOnClickListener {
            // Извлекаем текст из EditText
            val userInputText = _editTextLogin.text.toString()

            // Проверяем, не пустой ли текст
            if (userInputText.isNotBlank()) {
                // Используем getString() для форматированных строк из ресурсов
                val toastMessage = getString(R.string.toast_your_text, _editTextLogin.text)
                // текст в Toast-уведомление
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()

                // И текст в TextView на экране
                _textViewDisplay.text = userInputText
            } else {
                // Если поле ввода пустое
                Toast.makeText(this, "Пожалуйста, введите текст", Toast.LENGTH_SHORT).show()
                _textViewDisplay.text = "Вы ничего не ввели" // Очищаем или устанавливаем сообщение по умолчанию
            }
        }

        // Обработчик нажатия на кнопку "октрыть список"
        _buttonOpenList.setOnClickListener {
            // интент для перехода на новую активити
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent) // иии запуск активити
        }
    }

    /*
    * Методы для логирования всех событий формы
    */

    override fun onStart() {
        super.onStart()
        // Логируем вызов метода onStart
        Log.d(TAG, "onStart: Activity становится видимым")
    }

    override fun onResume() {
        super.onResume()
        // Логируем вызов метода onResume
        Log.d(TAG, "onResume: Activity готово к взаимодействию с пользователем")
    }

    override fun onPause() {
        super.onPause()
        // Логируем вызов метода onPause
        Log.d(TAG, "onPause: Activity приостановлено")
    }

    override fun onStop() {
        super.onStop()
        // Логируем вызов метода onStop
        Log.d(TAG, "onStop: Activity больше не видно пользователю")
    }

    override fun onRestart() {
        super.onRestart()
        // Логируем вызов метода onRestart
        Log.d(TAG, "onRestart: Activity перезапускается после остановки")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Логируем вызов метода onDestroy
        Log.d(TAG, "onDestroy: Activity уничтожается")
    }
}