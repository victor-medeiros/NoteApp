package com.victor.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            TextChip(
                isSelected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Title(noteOrder.orderType))
                },
                text = "Title",
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextChip(
                isSelected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Date(noteOrder.orderType))
                },
                text = "Date",
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextChip(
                isSelected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onChangeNoteOrder(NoteOrder.Color(noteOrder.orderType))
                },
                text = "Color",
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextChip(
                isSelected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onChangeNoteOrder(noteOrder.copy())
                },
                text = "Ascending",
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextChip(
                isSelected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onChangeNoteOrder(noteOrder.copy())
                },
                text = "Descending",
            )
        }
    }
}