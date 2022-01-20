package com.victor.noteapp.features.note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.noteapp.features.note.core.Constants
import com.victor.noteapp.features.note.domain.model.InvalidNoteException
import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _titleState = mutableStateOf(BasicTextFieldState(
        hint = "Enter the note title"
    ))
    private val titleState: State<BasicTextFieldState> = _titleState

    private val _contentState = mutableStateOf(BasicTextFieldState(
        hint = "Write the note content"
    ))
    private val contentState: State<BasicTextFieldState> = _contentState

    private val _colorState = mutableStateOf(Note.colors.random().toArgb())
    private val colorState: State<Int> = _colorState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var noteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(id)?.also { note ->
                        noteId = id
                        _titleState.value = titleState.value.copy(
                            text = note.title
                        )
                        _contentState.value = contentState.value.copy(
                            text = note.content
                        )
                        _colorState.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.ChangeTitleText -> {
                _titleState.value = titleState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.FocusTitleTextField -> {
                _titleState.value = titleState.value.copy(
                    isHintVisible = event.focusState.isFocused
                )
            }
            is AddEditNoteEvent.ChangeContentText -> {
                _contentState.value = contentState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.FocusContentTextField -> {
                _contentState.value = contentState.value.copy(
                    isHintVisible = event.focusState.isFocused
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _colorState.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = titleState.value.text,
                                content = contentState.value.text,
                                color = colorState.value,
                                timestamp = System.currentTimeMillis(),
                                id = noteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            e.message ?: Constants.UNEXPECTED_ERROR
                        ))
                    }
                }
            }
        }
    }

    sealed class UiEvent() {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}