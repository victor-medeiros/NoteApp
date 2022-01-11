package com.victor.noteapp.features.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.victor.noteapp.ui.theme.LightBlue
import com.victor.noteapp.ui.theme.LightGreen
import com.victor.noteapp.ui.theme.Red
import com.victor.noteapp.ui.theme.Yellow

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colors = listOf(LightGreen, LightBlue, Yellow, Red)
    }
}
