package com.example.notesapplication.ui.uimodel

import android.os.Parcelable
import com.example.notesapplication.data.local.Note
import kotlinx.parcelize.Parcelize


@Parcelize
data class NoteUiModel(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val lastUpdate: Long = System.currentTimeMillis(),
    var isSelected: Boolean = false
) : Parcelable

fun Note.mapToUiModel() = NoteUiModel(
    id = id, title = title, content = content, lastUpdate = timestamp
)

fun NoteUiModel.mapToEntityModel() = Note(
    id = id, title = title, content = content, timestamp = lastUpdate
)