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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var repository: NoteRepository = NoteRepository(
        NoteDatabase.getDatabase(application).noteDao()
    )

    var allNotes: LiveData<List<NoteUiModel>>? = null

    private val _executeDataResult = MutableLiveData<ExecuteDbResult>()
    val executeDataResult: LiveData<ExecuteDbResult> = _executeDataResult

    init {
        allNotes =
            repository.allNotes.switchMap { notes -> MutableLiveData(notes.map { it.mapToUiModel() }) }
    }
    fun insert(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.insert(note.mapToEntityModel())
        handleDataResult(result != -1L)
    }

    fun delete(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.delete(note.mapToEntityModel())
        handleDataResult(result == 1)
    }

    fun update(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.update(note.mapToEntityModel())
        handleDataResult(result == 1)
    }

    private fun handleDataResult(isSuccess: Boolean) {
        val result = if (isSuccess) {
            ExecuteDbResult.Success
        } else {
            ExecuteDbResult.Error
        }
        _executeDataResult.postValue(result)
    }

}

sealed class ExecuteDbResult {
    data object Error : ExecuteDbResult()
    data object Success : ExecuteDbResult()

}