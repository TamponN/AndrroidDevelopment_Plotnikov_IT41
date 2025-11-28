package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

// Имплементация интерфейса, созданного в ListFragment
class FragmentActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {
    private val TAG = "FragmentLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        Log.d(TAG, "FragmentHostActivity: onCreate")

        // Добавляем фрагменты только если это первый запуск (savedInstanceState == null)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerList, ListFragment()) // Добавляем ListFragment
                .add(R.id.fragmentContainerDetail, DetailFragment()) // Добавляем DetailFragment по умолчанию
                .commit()
        }
    }

    // Обработчик выбора элемента в ListFragment
    override fun onItemSelected(itemName: String) {
        // Создаем новый экземпляр DetailFragment с переданными данными
        val detailFragment = DetailFragment.newInstance(itemName)

        // Заменяем старый DetailFragment на новый с анимацией
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerDetail, detailFragment)
            .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Анимация
            .commit()
    }

    // Логи
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Старт")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Продолжение")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Пауза")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Остановка")
    }
}
