package com.example.notesapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.data.local.NoteDatabase
import com.example.notesapplication.data.repository.NoteRepository
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.ui.uimodel.mapToEntityModel
import com.example.notesapplication.ui.uimodel.mapToUiModel
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy {
        NoteRepository(
            NoteDatabase.getDatabase(application).noteDao()
        )
    }
    val allNotes: LiveData<List<NoteUiModel>> =
        repository.allNotes.switchMap { notes -> MutableLiveData(notes.map { it.mapToUiModel() }) }

    fun insert(note: NoteUiModel) = viewModelScope.launch {
        repository.insert(note.mapToEntityModel())
    }

    fun delete(note: NoteUiModel) = viewModelScope.launch {
        repository.delete(note.mapToEntityModel())
    }

    fun update(note: NoteUiModel) = viewModelScope.launch {
        repository.update(note.mapToEntityModel())
    }
}
