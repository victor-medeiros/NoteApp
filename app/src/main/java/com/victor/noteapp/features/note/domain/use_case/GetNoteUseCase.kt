package com.victor.noteapp.features.note.domain.use_case

import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNote(id)
    }
}