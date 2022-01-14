package com.victor.noteapp.features.note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.domain.use_case.NoteUseCases
import com.victor.noteapp.features.note.domain.utils.NoteOrder
import com.victor.noteapp.features.note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
    private val navigation: Navigation
): ViewModel() {
    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private var recentlyDeletedNote: Note? = null

    private var noteJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if (event.noteOrder::class == notesState.value.noteOrder::class
                    && event.noteOrder.orderType == notesState.value.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _notesState.value = notesState.value.copy(
                    isOrderSectionVisible = !notesState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        noteJob?.cancel()
        noteJob = notesUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _notesState.value = notesState.value.copy(
                notes = notes
                )
            }
            .launchIn(viewModelScope)
    }
}