package com.victor.noteapp.features.note.presentation.add_edit_note

data class BasicTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val focusState: Boolean = true
)
