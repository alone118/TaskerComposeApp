package com.example.composetaskerapp.domain.usecases

import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.repository.TaskCategoryRepository
import kotlinx.coroutines.flow.Flow

class AddTaskCategoryUseCases(
    private val repository: TaskCategoryRepository
) {

    operator fun invoke(taskCategory: TaskCategory){
        repository.addTaskCategory(taskCategory)
    }

}