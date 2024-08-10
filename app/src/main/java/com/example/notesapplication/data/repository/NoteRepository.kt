package com.example.notesapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.notesapplication.data.local.Note
import com.example.notesapplication.data.local.NoteDao

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun update(note: Note) = noteDao.update(note)

}
