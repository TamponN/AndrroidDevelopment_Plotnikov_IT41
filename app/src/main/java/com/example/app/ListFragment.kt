package com.example.app

// Необходимые импорты
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {
    // Создаем интерфейс для обратного вызова (взаимодействия с Activity)
    interface OnItemSelectedListener {
        fun onItemSelected(itemName: String)
    }

    private var listener: OnItemSelectedListener? = null
    private val TAG = "FragmentLifecycle"

    // Вызывается при присоединении фрагмента к Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ListFragment: onAttach")

        // проверяем что context реализует интерфейс OnItemSelectedListener
        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context класс должен иmлeментировать OnItemSelectedListener")
        }
    }

    // Вызывается для создания иерархии View фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "ListFragment: onCreateView")
        // Возвращаем макет фрагмента
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    // Вызывается после того, как View было создано
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ListFragment: onViewCreated")

        // Находим кнопки и устанавливаем обработчики нажатий
        view.findViewById<Button>(R.id.buttonItem1).setOnClickListener {
            val itemName = "Элемент 1" // да, хардкодим
            // Сообщаем, что был выбран элемент
            listener?.onItemSelected(itemName)
        }

        view.findViewById<Button>(R.id.buttonItem2).setOnClickListener {
            val itemName = "Элемент 2"
            listener?.onItemSelected(itemName)
        }
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
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "Уничтожение")
    }
    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "Открепление")
        listener = null
    }
}
