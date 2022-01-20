package com.victor.noteapp.features.note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.victor.noteapp.features.note.domain.model.Note
import com.victor.noteapp.features.note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    navController: NavController,
    noteColor: Int
) {
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val colorState = viewModel.colorState.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val noteBackGroundAnimatable = remember {
        Animatable(
            Color(if (noteColor == -1) Note.colors.random().toArgb() else noteColor)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Ok"
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note",
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .background(noteBackGroundAnimatable.value)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                shape = CircleShape,
                                color = if (colorState == color.toArgb()) Color.Black else Color.Transparent
                            )
                            .clickable {
                                scope.launch {
                                    noteBackGroundAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(
                                    AddEditNoteEvent.ChangeColor(color.toArgb())
                                )
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                isHintVisible = titleState.isHintVisible,
                textStyle = MaterialTheme.typography.h5,
                singleLine = true,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleText(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.FocusTitleTextField(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                singleLine = false,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentText(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.FocusContentTextField(it))
                }
            )
        }
    }
}