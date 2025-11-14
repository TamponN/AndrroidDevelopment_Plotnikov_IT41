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
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts // импорт для использования ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity // импорт для использования Activity.RESULT_OK

// Новые импорты для меню настроек
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate

// Новые импорты для состояния приложения
import android.content.Context

class MainActivity : AppCompatActivity() { // меняем наследованный класс на AppCompatActivity

    // Объявляем TAG для логирования
    private val TAG = "MainActivityLifecycle"

    // Объявляем переменные для наших View элементов lateinit означает что мы инициализируем их позже
    private lateinit var _editTextLogin: EditText
    private lateinit var _buttonLogin: Button
    private lateinit var _textViewDisplay: TextView
    private lateinit var _buttonOpenList: Button
    private lateinit var _buttonOpenFragments: Button

    //Создаем обработчик результата от другого Activity
    private val getResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // код выполнится когда ListActivity вернет результат
        if (result.resultCode == Activity.RESULT_OK) {
            // Извлекаем данные из Intent
            val selectedName = result.data?.getStringExtra(ListActivity.SELECTED_NAME_KEY)

            // Проверяем что имя не пустое и отображаем его
            if (!selectedName.isNullOrEmpty()) {
                val displayText = "Выбрано имя: $selectedName"
                _textViewDisplay.text = displayText
                Toast.makeText(this, displayText, Toast.LENGTH_SHORT).show()
            }
        } else {
            // Если пользователь просто вернулся назад, не выбрав элемент
            Toast.makeText(this, "Имя не выбрано", Toast.LENGTH_SHORT).show()
        }
    }

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
        _buttonOpenFragments = findViewById(R.id.buttonOpenFragments)

        // Регистрация View для контекстного меню для текста на главной странице
        registerForContextMenu(_textViewDisplay)

        // Слушатель события для активити
        _buttonOpenFragments.setOnClickListener {
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }

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
            // Получаем текст из поля для логина, который будем передавать между активити
            val loginToPass = _editTextLogin.text.toString()

            // Проверяем, что поле не пустое
            if (loginToPass.isBlank()) {
                Toast.makeText(this, "Сначала введите логин для передачи", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Прерываем выполнение, если логин пуст
            }
            // интент для перехода на новую активити
            val intent = Intent(this, ListActivity::class.java)

            // передаём логин в Intent
            intent.putExtra(ListActivity.LOGIN_KEY, loginToPass)

            // Запускаем Activity с помощью лаунчера, ожидая результат
            getResultLauncher.launch(intent)
        }
        // грузим тему из настроек
        loadAndApplyTheme()

        // грузим пользователя из настроек
        loadUsername()
    }

    // Создание Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Открываем наше меню из XML-файла
        menuInflater.inflate(R.menu.main_options_menu, menu)
        return true
    }

    // Обработка события нажания на элементы меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // запуск окна настроек
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_show_dialog -> {
                // вызов функции, которая покажет диалог
                showCustomDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Обработка события создания контекстного меню
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Проверяем, для какого View создается меню (допом можно будет ещё условий добавить)
        if (v?.id == R.id.textViewDisplay) {
            // Открываем наше контекстное меню из XML
            menuInflater.inflate(R.menu.context_menu, menu)
        }
    }

    // Обработка нажатий на пункты контекстного меню
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_action_edit -> {
                Toast.makeText(this, "Выбран пункт 'Редактировать'", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.context_action_delete -> {
                // Пока сделаем так, что при нажании кнопки "удалить" будет открываться стандартный диалог
                showCustomDialog()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // Метод для показа стандартного алерта (не используется на данный момент)
    private fun showStandardAlertDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Стандартный диалог") // Заголовок
            .setMessage("Вы действительно хотите выполнить это действие?") // Сообщение
            .setIcon(R.drawable.ic_app_logo) // Иконка
            .setPositiveButton("Да") { dialog, _ ->
                // нажатие "Да"
                Toast.makeText(this, "Вы нажали 'Да'", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                // нажатие "Нет"
                Toast.makeText(this, "Вы нажали 'Нет'", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNeutralButton("Отмена") { dialog, _ ->
                // нажатие "Отмена"
                dialog.dismiss() // удаляем диалог
            }

        val dialog = builder.create()
        dialog.show()
    }

    // Метод для показа кастомного диалога
    private fun showCustomDialog() {
        // Открываем кастомный макет
        val customDialogView = layoutInflater.inflate(R.layout.dialog_custom, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(customDialogView) // Устанавливаем наш кастомный View

        val dialog = builder.create()

        // Находим элементы внутри кастомного диалога и вешаем обработчики
        val editTextReason = customDialogView.findViewById<EditText>(R.id.editTextReason)
        val buttonConfirm = customDialogView.findViewById<Button>(R.id.buttonConfirm)
        val buttonCancel = customDialogView.findViewById<Button>(R.id.buttonCancel)

        buttonConfirm.setOnClickListener {
            val reason = editTextReason.text.toString()
            Toast.makeText(this, "Причина: $reason", Toast.LENGTH_LONG).show()
            _textViewDisplay.text = "Элемент удален" // Имитируем удаление
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    //  метод для загрузки и применения темы
    private fun loadAndApplyTheme() {
        val sharedPrefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // метод для загрузки имени пользователя в поле ввода
    private fun loadUsername() {
        val sharedPrefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")
        _editTextLogin.setText(username) // Устанавливаем загруженное имя в поле
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