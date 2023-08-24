package com.example.composetaskerapp.common.extension

import com.example.composetaskerapp.data.models.TaskCache
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.presentation.models.TaskUi

fun Task.mapToCache(): TaskCache {
    return TaskCache(
        id = id,
        title = title,
        time = time,
        data = data,
        categoryId = categoryId,
        categoryColor = categoryColor
    )
}

fun TaskCache.mapToTask(): Task {
    return Task(
        id = id,
        title = title,
        time = time,
        data = data,
        categoryId = categoryId,
        categoryColor = categoryColor
    )
}

fun Task.mapToTaskUi(): TaskUi {
    return TaskUi(
        id = id,
        title = title,
        time = time,
        data = data,
        categoryId = categoryId,
        categoryColor = categoryColor
    )
}