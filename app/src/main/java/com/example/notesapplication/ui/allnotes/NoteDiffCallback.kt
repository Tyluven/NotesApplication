package com.example.notesapplication.ui.allnotes

import androidx.recyclerview.widget.DiffUtil
import com.example.notesapplication.ui.uimodel.NoteUiModel

class NoteDiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
    override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem == newItem
    }
}
