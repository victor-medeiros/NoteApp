package com.victor.noteapp.features.note.presentation.add_edit_note

import android.util.Log
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
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _titleState = mutableStateOf(BasicTextFieldState(
        hint = "Enter the note title"
    ))
    val titleState: State<BasicTextFieldState> = _titleState

    private val _contentState = mutableStateOf(BasicTextFieldState(
        hint = "Write the note content"
    ))
    val contentState: State<BasicTextFieldState> = _contentState

    private val _colorState = mutableStateOf(Note.colors.random().toArgb())
    val colorState: State<Int> = _colorState

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
                            text = note.title,
                            isHintVisible = note.title.isBlank()
                        )
                        _contentState.value = contentState.value.copy(
                            text = note.content,
                            isHintVisible = note.content.isBlank()
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
                val isHintVisible = !event.focusState.isFocused
                        && titleState.value.text.isBlank()
                Log.d("Title", ":::")
                Log.d("isFocused", (!event.focusState.isFocused).toString())
                Log.d("isBlank", contentState.value.text)
                _titleState.value = titleState.value.copy(
                    isHintVisible = isHintVisible
                )
            }
            is AddEditNoteEvent.ChangeContentText -> {
                _contentState.value = contentState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.FocusContentTextField -> {
                val isHintVisible = !event.focusState.isFocused
                        && contentState.value.text.isBlank()
                Log.d("isHintVisible", isHintVisible.toString())
                _contentState.value = contentState.value.copy(
                    isHintVisible = isHintVisible
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