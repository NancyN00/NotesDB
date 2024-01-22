package com.example.roomdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdb.model.Note
import com.example.roomdb.repository.NoteRepository
import kotlinx.coroutines.launch

//extend as android view model
class NoteViewModel(app : Application, private val noteRepository: NoteRepository) : AndroidViewModel(app) {

    //use if viewModelScope ensures that coroutine is cancelled when associate viewmodel is cleared to prevent potential memory leak
    //launch in the background using coroutine

    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    //retrieving all data there is no need of parameter
     fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String?) =
        noteRepository.searchNotes(query)


}

