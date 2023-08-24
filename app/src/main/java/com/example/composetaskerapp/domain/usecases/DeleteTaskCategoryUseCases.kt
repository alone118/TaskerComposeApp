package com.example.composetaskerapp.domain.usecases

import com.example.composetaskerapp.domain.repository.TaskCategoryRepository

class DeleteTaskCategoryUseCases(
    private val repository: TaskCategoryRepository
) {

    operator fun invoke(id: String) {
        repository.deleteTaskCategoryById(id)
    }
}