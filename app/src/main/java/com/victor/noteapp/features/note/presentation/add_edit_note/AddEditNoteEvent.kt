package com.victor.noteapp.features.note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class ChangeTitleText(val value: String): AddEditNoteEvent()
    data class FocusTitleTextField(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeContentText(val value: String): AddEditNoteEvent()
    data class FocusContentTextField(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
