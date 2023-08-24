package com.example.composetaskerapp.presentation.models

import com.example.composetaskerapp.domain.models.Task
import java.util.UUID

data class TaskUi(
    val id: String,
    val title: String,
    val time: String,
    val data: String,
    val categoryId: String,
    var isSelected: Boolean = false,
    var categoryColor:String
){
    companion object {
        val preview = TaskUi(
            id = UUID.randomUUID().toString(),
            time = "22:00",
            data = "04.07.2023",
            title = "Go to school",
            categoryId = "",
            categoryColor = ""
        )
        val previews = listOf(
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
        )
    }
}