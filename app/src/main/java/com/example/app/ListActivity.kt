package com.example.app

// Необходимые импорты
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    // Объявление полей класса
    private lateinit var _recyclerViewNames: RecyclerView
    private lateinit var _nameAdapter: NameAdapter
    private lateinit var _namesList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет
        setContentView(R.layout.activity_list)

        // Инициализируем вьюху по id элемента
        _recyclerViewNames = findViewById(R.id.recyclerViewNames)

        // Устанавливаем менеджера в линейном списке
        _recyclerViewNames.layoutManager = LinearLayoutManager(this)

        // Заполняем список имён
        prepareNamesData()
        // Создаем экземпляр  адаптера, передавая ему список имен
        _nameAdapter = NameAdapter(_namesList)
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