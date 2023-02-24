package com.victor.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.ui.theme.LightBlue

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    cornerRadius: Dp = 10.dp,
    onDelete: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        elevation = 16.dp
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(note.color))
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note"
                )
            }
        }
    }
}

@Preview(widthDp = 400, heightDp = 100, showBackground = true)
@Composable
fun NoteItemPreview() {
    Surface() {
        NoteItem(
            note = Note(
                title = "Teste",
                content = LoremIpsum(words = 50).values.first(),
                timestamp = System.currentTimeMillis(),
                color = LightBlue.toArgb()
            ),
            modifier = Modifier.padding(8.dp),
            onDelete = {  }
        )
    }
}