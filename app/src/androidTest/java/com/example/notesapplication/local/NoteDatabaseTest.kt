package com.example.notesapplication.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapplication.data.local.Note
import com.example.notesapplication.data.local.NoteDao
import com.example.notesapplication.data.local.NoteDatabase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        noteDao = database.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNote_Case01() = runBlocking {
        val note = Note(id = 15, title = "Title", content = "Content")
        val rowId = noteDao.insert(note)
        assertTrue(rowId.toInt() == note.id)
    }

    @Test
    fun insertNote_Case02() = runBlocking {
        val note = Note(id = 8, title = "Title", content = "Content")
        val rowId = noteDao.insert(note)
        assertTrue(rowId.toInt() == note.id)
    }

    @Test
    fun insertNote_Case03() = runBlocking {
        val note = Note(id = 3, title = "Title", content = "Content")
        val rowId = noteDao.insert(note)
        assertTrue(rowId.toInt() == note.id)
    }

    @Test
    fun insertNote_Case04() = runBlocking {
        val note = Note(id = 11, title = "Title", content = "Content")
        noteDao.insert(note)
        val rowId = noteDao.insert(note)
        assertTrue(rowId == -1L)
    }

    @Test
    fun deleteNote_Case01() = runBlocking {
        val note = Note(id = 12, title = "Title", content = "Content")
        val note2 = Note(id = 13, title = "Title", content = "Content")
        val note3 = Note(id = 14, title = "Title", content = "Content")
        noteDao.insert(note)
        noteDao.insert(note2)
        noteDao.insert(note3)
        val result3 = noteDao.delete(note3)
        val result2 = noteDao.delete(note2)
        val result1 = noteDao.delete(note)
        assertTrue(result3 == 1 && result2 == 1 && result1 == 1)
    }

    @Test
    fun deleteNote_Case02() = runBlocking {
        val note = Note(id = 12, title = "Title", content = "Content")
        val note2 = Note(id = 13, title = "Title", content = "Content")
        val note3 = Note(id = 14, title = "Title", content = "Content")
        noteDao.insert(note)
        noteDao.insert(note2)
        noteDao.insert(note3)
        noteDao.delete(note3)
        val result = noteDao.delete(note3)
        assertTrue(result != 1)
    }

    @Test
    fun deleteNote_Case03() = runBlocking {
        val note = Note(id = 12, title = "Title", content = "Content")
        val result = noteDao.delete(note)
        assertTrue(result == 0)
    }

    @Test
    fun updateNote_Case01() = runBlocking {
        val note = Note(id = 12, title = "Title", content = "Content")
        val note2 = Note(id = 13, title = "Title", content = "Content")
        val note3 = Note(id = 14, title = "Title", content = "Content")
        noteDao.insert(note)
        noteDao.insert(note2)
        noteDao.insert(note3)

        val updatedNote1 = note.copy(title = "Updated1 Title")
        val result1 = noteDao.update(updatedNote1)

        val updatedNote3 = note3.copy(title = "Updated3 Title")
        val result3 = noteDao.update(updatedNote3)

        assertTrue(result1 == 1 && result3 == 1)
    }

    @Test
    fun updateNote_Case02() = runBlocking {
        val note = Note(id = 12, title = "Title", content = "Content")

        val updatedNote1 = note.copy(title = "Updated1 Title")
        val result1 = noteDao.update(updatedNote1)

        assertTrue(result1 != 1)
    }

}
