package com.example.app

// Нужные импорты
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Адаптер для списка имен
class NameAdapter(
    private val names: List<String>,
    private val onItemClick: (String) -> Unit // Принимаем строку
) : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    // Хранит ссылки на View-компоненты каждого элемента списка, шобы постоянно по id не искать
    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Получаем ссылку на TextView из макета
        val nameTextView: TextView = itemView.findViewById(R.id.textViewItemName)
    }

    // Вызывается когда списку нужен новый холдер для отображения элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        // Создаем View из макета
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_name, parent, false)

        // Возвращаем новый экземпляр
        return NameViewHolder(itemView)
    }

    // Вызывается чтобы отобразить данные в указанной позиции
    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        // Получаем имя для текущего индекса
        val currentName = names[position]
        // Устанавливаем текст
        holder.nameTextView.text = currentName
        // Устанавливаем слушатель на весь элемент списка
        holder.itemView.setOnClickListener {
            // Вызываем переданную нам функцию, передавая в нее имя нажатого элемента
            onItemClick(currentName)
        }
    }

    // А это для получения общего кол-ва элементов в списке имён
    override fun getItemCount(): Int {
        return names.size
    }
}