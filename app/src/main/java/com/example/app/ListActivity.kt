package com.example.app

// Необходимые импорты
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import android.content.Intent

class ListActivity : AppCompatActivity() {

    // Объявление полей класса
    private lateinit var _recyclerViewNames: RecyclerView
    private lateinit var _nameAdapter: NameAdapter
    private lateinit var _namesList: ArrayList<String>

    // Создаем companion object для хранения ключей
    companion object {
        const val LOGIN_KEY = "LOGIN_DATA" // Ключ для получения логина
        const val SELECTED_NAME_KEY = "SELECTED_NAME" // Ключ для возврата имени
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет
        setContentView(R.layout.activity_list)

        val receivedLogin = intent.getStringExtra(LOGIN_KEY) // Получаем данные из Intent

        // Отображаем полученный логин в Toast
        if (!receivedLogin.isNullOrEmpty()) {
            Toast.makeText(this, "Получен логин: $receivedLogin", Toast.LENGTH_LONG).show()
        }

        // Инициализируем вьюху по id элемента
        _recyclerViewNames = findViewById(R.id.recyclerViewNames)
        // Устанавливаем менеджера в линейном списке
        _recyclerViewNames.layoutManager = LinearLayoutManager(this)

        // Заполняем список имён
        prepareNamesData()
        // Создаем экземпляр  адаптера, передавая ему список имен
        _nameAdapter = NameAdapter(_namesList) { selectedName ->
            // Создаем Intent для возврата данных
            val resultIntent = Intent()
            // Кладем в него выбранное имя
            resultIntent.putExtra(SELECTED_NAME_KEY, selectedName)
            // Устанавливаем результат (RESULT_OK) и Intent с данными
            setResult(Activity.RESULT_OK, resultIntent)
            // Завершаем текущее Activity, чтобы вернуться на предыдущее
            finish() // Логика такова, что по нажатию на любое имя происходит возврат на предыдущий экран
        }

        // Устанавливаем адаптер для вьюхи
        _recyclerViewNames.adapter = _nameAdapter
    }

    // Метод для подготовки списка имен
    private fun prepareNamesData() {
        _namesList = ArrayList()
        _namesList.add("Никита")
        _namesList.add("Юра")
        _namesList.add("Олег")
        _namesList.add("Антон")
        _namesList.add("Грызло")
        _namesList.add("Епинафий")
        _namesList.add("1С")
        _namesList.add("Python")
        _namesList.add("Алё")
        _namesList.add("Договорились")
        _namesList.add("Чотко")
        _namesList.add("Рунальду")
        _namesList.add("Фамилия")
        _namesList.add("Нет")
        _namesList.add("Посмотрим")
    }
}