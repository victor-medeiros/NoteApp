package com.victor.noteapp.features.note.presentation.notes.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.noteapp.ui.theme.Shapes

@Composable
fun TextChip(
    isSelected: Boolean,
    onSelect: () -> Unit,
    text: String,
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = Shapes.medium
            )
            .background(
                color = if (isSelected) MaterialTheme.colors.onBackground else Transparent,
                shape = Shapes.medium
            )
            .padding(10.dp, 5.dp)
            .clickable {
                onSelect()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = if (isSelected) MaterialTheme.colors.onSurface else MaterialTheme.colors.onBackground
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RadioButtonPreview() {
    val isSelected = remember {
        mutableStateOf(false)
    }
    TextChip(
        isSelected = isSelected.value,
        onSelect = { isSelected.value = !isSelected.value },
        text = "Teste",
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RadioButtonSelectedPreview() {
    val isSelected = remember {
        mutableStateOf(true)
    }
    TextChip(
        isSelected = isSelected.value,
        onSelect = { isSelected.value = !isSelected.value },
        text = "Teste",
    )
}