package com.victor.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.Navigation
import com.victor.noteapp.features.note.presentation.notes.NotesEvent
import com.victor.noteapp.features.note.presentation.notes.NotesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigation: Navigation
) {
    val state = viewModel.notesState.value

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "My notes")
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = "Sort notes"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OrderSection(
            modifier = Modifier.fillMaxWidth(),
            noteOrder = state.noteOrder,
            onChangeNoteOrder = { viewModel.onEvent(NotesEvent.Order(state.noteOrder)) }
        )
        LazyColumn() {
            items(state.notes) { note ->

            }
        }
    }
}