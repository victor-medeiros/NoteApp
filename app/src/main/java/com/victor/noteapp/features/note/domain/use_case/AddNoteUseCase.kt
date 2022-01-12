package com.victor.noteapp.features.note.domain.use_case

import com.victor.noteapp.features.note.domain.model.InvalidNoteException
import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Note title can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Note content can't be empty.")
        }
        noteRepository.addNote(note)
    }
}