package com.victor.noteapp.features.note.domain.use_case

import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}