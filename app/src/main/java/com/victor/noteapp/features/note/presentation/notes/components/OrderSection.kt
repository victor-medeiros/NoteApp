package com.victor.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.victor.noteapp.features.note.domain.utils.NoteOrder
import com.victor.noteapp.features.note.domain.utils.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder,
    onChangeNoteOrder: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Title(noteOrder.orderType))
                },
                text = "Title"
            )
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Date(noteOrder.orderType))
                },
                text = "Date"
            )
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Color(noteOrder.orderType))
                },
                text = "Color"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onChangeNoteOrder(noteOrder.copy())
                },
                text = "Ascending"
            )
            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onChangeNoteOrder(noteOrder.copy())
                },
                text = "Ascending"
            )
        }
    }
}