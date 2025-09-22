package com.example.app

// Импорт библиотек для логирования и работы с экранными компонентами
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
// Импорт библы для уведомлений
import android.widget.Toast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.text
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    // Объявляем TAG для логирования
    private val TAG = "MainActivityLifecycle"

    // Объявляем переменные для наших View элементов lateinit означает что мы инициализируем их позже
    private lateinit var editTextLogin: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // включение отображения под системными элементами
        // Ставим свой макет в качестве основного
        setContentView(R.layout.activity_main)
        // Лог вызова метода onCreate
        Log.d(TAG, "onCreate: Activity создается")

        // Инициализируем наши View элементы, находя их по ID из XML-макета
        editTextLogin = findViewById(R.id.editTextLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewDisplay = findViewById(R.id.textViewDisplay)

        // Слушатель события на нажатие кнопки
        buttonLogin.setOnClickListener {
            // Извлекаем текст из EditText
            val userInputText = editTextLogin.text.toString()

            // Проверяем, не пустой ли текст
            if (userInputText.isNotBlank()) {
                // текст в Toast-уведомление
                Toast.makeText(this, "Ваш текст: $userInputText", Toast.LENGTH_LONG).show()

                // И текст в TextView на экране
                textViewDisplay.text = userInputText
            } else {
                // Если поле ввода пустое
                Toast.makeText(this, "Пожалуйста, введите текст", Toast.LENGTH_SHORT).show()
                textViewDisplay.text = "Вы ничего не ввели" // Очищаем или устанавливаем сообщение по умолчанию
            }
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