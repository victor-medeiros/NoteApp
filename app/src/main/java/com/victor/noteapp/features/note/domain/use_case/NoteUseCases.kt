package com.victor.noteapp.features.note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNote: GetNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNoteUse: AddNoteUseCase
)