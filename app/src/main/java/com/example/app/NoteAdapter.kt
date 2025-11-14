package com.example.app

// Нужные импорты
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Адаптер для списка имен
class NoteAdapter(
    private var notes: List<Note>, // передаём ему список записей
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ИЗМЕНЕНИЕ: Теперь у нас два TextView в макете
        val titleTextView: TextView = itemView.findViewById(R.id.textViewItemTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewItemDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        //Заполняем оба поля
        holder.titleTextView.text = currentNote.title
        holder.descriptionTextView.text = currentNote.description

        holder.itemView.setOnClickListener {
            onItemClick(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    // обновление данных в адаптере
    fun updateData(newNotes: List<Note>) {
        this.notes = newNotes
        notifyDataSetChanged() // Уведомляем RecyclerView о том, что данные изменились
    }
}