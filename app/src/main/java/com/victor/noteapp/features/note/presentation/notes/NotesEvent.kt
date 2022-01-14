package com.victor.noteapp.features.note.presentation.notes

import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}