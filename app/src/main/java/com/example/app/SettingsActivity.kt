package com.example.app

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var _switchDarkMode: SwitchMaterial
    private lateinit var _editTextSavedUsername: EditText
    private lateinit var _buttonSaveSettings: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Инициализируем вьюхи
        _switchDarkMode = findViewById(R.id.switchDarkMode)
        _editTextSavedUsername = findViewById(R.id.editTextSavedUsername)
        _buttonSaveSettings = findViewById(R.id.buttonSaveSettings)

        // грузим  текузщие настройки
        loadSettings()

        // обработчик кнопки
        _buttonSaveSettings.setOnClickListener {
            saveSettings()
        }
    }

    private fun loadSettings() {
        // SharedPreferences
        val sharedPrefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // забираем состояние поля
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        _switchDarkMode.isChecked = isDarkMode // вписываем в поле

        // и сохранённое имя
        val username = sharedPrefs.getString("username", "")
        _editTextSavedUsername.setText(username) // вписываем
    }

    private fun saveSettings() {
        // SharedPreferences
        val sharedPrefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // записывем режим
        val isDarkMode = _switchDarkMode.isChecked
        editor.putBoolean("dark_mode", isDarkMode)

        // записываем имя пользователя
        val username = _editTextSavedUsername.text.toString()
        editor.putString("username", username)

        editor.apply()

        // вызываем метод применения темы
        applyTheme(isDarkMode)
        Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_SHORT).show()

        // Завершаем активити
        finish()
    }

    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            // Устанавливаем темную тему
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
