package com.victor.noteapp.features.note.domain.utils

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun copy(): NoteOrder {
        val currentOrderType =
            if (this.orderType == OrderType.Ascending)
                OrderType.Descending
            else
                OrderType.Ascending
        return when (this) {
            is Date -> Date(currentOrderType)
            is Title -> Title(currentOrderType)
            is Color -> Color(currentOrderType)
        }
    }
}
