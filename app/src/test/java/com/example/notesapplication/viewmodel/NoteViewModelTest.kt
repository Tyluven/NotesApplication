package com.example.notesapplication.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapplication.data.repository.NoteRepository
import com.example.notesapplication.extensions.getOrAwaitValue
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.ui.uimodel.mapToEntityModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel

    private var repository: NoteRepository = mockk(relaxed = true)

    private var application: Application = mockk(relaxed = true)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = NoteViewModel(application)
        viewModel.repository = repository
    }

    @Test
    fun insertNote_Case01() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.insert(note.mapToEntityModel())
        } returns -1L
        viewModel.insert(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Error)
    }

    @Test
    fun insertNote_Case02() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.insert(note.mapToEntityModel())
        } returns note.id.toLong()
        viewModel.insert(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Success)
    }

    @Test
    fun insertNote_Case03() = runBlocking {
        val note = NoteUiModel(id = 11, title = "Note Title", content = "Content")
        coEvery {
            repository.insert(note.mapToEntityModel())
        } returns note.id.toLong()
        viewModel.insert(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Success)
    }


    @Test
    fun updateNote_Case01() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.update(note.mapToEntityModel())
        } returns -1
        viewModel.update(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Error)
    }

    @Test
    fun updateNote_Case02() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.update(note.mapToEntityModel())
        } returns 1
        viewModel.update(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Success)
    }

    @Test
    fun deleteNote_Case01() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.delete(note.mapToEntityModel())
        } returns -1
        viewModel.delete(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Error)
    }

    @Test
    fun deleteNote_Case02() = runBlocking {
        val note = NoteUiModel(title = "Note Title", content = "Content")
        coEvery {
            repository.delete(note.mapToEntityModel())
        } returns 1
        viewModel.delete(note)
        val executeDataResult = viewModel.executeDataResult.getOrAwaitValue()
        assertTrue(executeDataResult is ExecuteDbResult.Success)
    }

}
