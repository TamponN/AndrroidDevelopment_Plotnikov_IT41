package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {
    private val TAG = "FragmentLifecycle"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "DetailFragment: onCreateView")
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "DetailFragment: onViewCreated")

        // Получаем аргументы, которые были переданы фрагменту
        val itemText = arguments?.getString(ARG_ITEM_NAME) ?: "Выберите элемент из списка"

        // Находим TextView и устанавливаем текст
        view.findViewById<TextView>(R.id.textViewDetail).text = itemText
    }

    // Статический метод для создания экземпляра фрагмента с аргументами
    companion object {
        private const val ARG_ITEM_NAME = "item_name"

        fun newInstance(itemName: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ARG_ITEM_NAME, itemName)
            fragment.arguments = args
            return fragment
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
    }
}
