package com.victor.noteapp.features.note.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
