package com.victor.noteapp.features.note.presentation.notes

import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.utils.NoteOrder
import com.victor.noteapp.features.note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
