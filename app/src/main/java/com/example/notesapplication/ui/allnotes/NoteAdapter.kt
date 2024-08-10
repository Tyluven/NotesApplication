package com.example.notesapplication.ui.allnotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.databinding.ItemNoteBinding
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.util.DateUtil

class NoteAdapter : ListAdapter<NoteUiModel, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    var onItemClickCallback: ((NoteUiModel) -> Unit)? = null

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteUiModel) = with(binding) {
            tvNoteTitle.text = note.title
            tvNoteContent.text = note.content
            tvLastUpdate.text = DateUtil.getDate(note.lastUpdate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.invoke(currentNote)
        }
    }
}
