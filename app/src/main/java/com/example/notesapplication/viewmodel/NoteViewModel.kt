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
    private val repository by lazy {
        NoteRepository(
            NoteDatabase.getDatabase(application).noteDao()
        )
    }
    val allNotes: LiveData<List<NoteUiModel>> =
        repository.allNotes.switchMap { notes -> MutableLiveData(notes.map { it.mapToUiModel() }) }

    private val _executeDataResult = MutableLiveData<ExecuteDbResult>()
    val executeDataResult: LiveData<ExecuteDbResult> = _executeDataResult

    fun insert(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        _executeDataResult.postValue(ExecuteDbResult.Loading)
        val row = repository.insert(note.mapToEntityModel())
        handleDataResult(row >= 0)
    }

    fun delete(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        _executeDataResult.postValue(ExecuteDbResult.Loading)
        val row = repository.delete(note.mapToEntityModel())
        handleDataResult(row >= 0)
    }

    fun update(note: NoteUiModel) = viewModelScope.launch(Dispatchers.IO) {
        _executeDataResult.postValue(ExecuteDbResult.Loading)
        val row = repository.update(note.mapToEntityModel())
        handleDataResult(row >= 0)
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
    data object Loading : ExecuteDbResult()
    data object Error : ExecuteDbResult()
    data object Success : ExecuteDbResult()

}