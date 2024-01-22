package com.example.roomdb.repository

import com.example.roomdb.database.NoteDatabase
import com.example.roomdb.model.Note

class NoteRepository (private val db : NoteDatabase) {

    //call the query which was created in Dao
    //now call them as method in their respective functions

    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    fun getAllNotes() = db.getNoteDao().getAllAllNotes()
    fun searchNotes(query: String?) = db.getNoteDao().searchNote(query)

}