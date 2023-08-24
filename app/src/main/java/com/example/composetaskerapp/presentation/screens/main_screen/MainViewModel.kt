package com.example.composetaskerapp.presentation.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetaskerapp.common.extension.mapToTaskUi
import com.example.composetaskerapp.data.repository.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.data.repository.TaskRepositoryImpl
import com.example.composetaskerapp.domain.repository.TaskRepository
import com.example.composetaskerapp.domain.usecases.FetchAllTaskCategoryUseCases
import com.example.composetaskerapp.domain.usecases.FetchAllTaskUseCases
import com.example.composetaskerapp.domain.usecases.FetchTasksSizeByCategoryIdUseCases
import com.example.composetaskerapp.domain.usecases.RemoveTasksByIdsUseCase
import com.example.composetaskerapp.presentation.models.TaskUi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {

    private val repository: TaskRepository = TaskRepositoryImpl()
    private val taskCategoryRepository = TaskCategoryRepositoryImpl()

    private val fetchAllTasksUseCases = FetchAllTaskUseCases(repository)
    private val removeTasksByIdsUseCase = RemoveTasksByIdsUseCase(repository)
    private val fetchAllTaskCategoryUseCases = FetchAllTaskCategoryUseCases(taskCategoryRepository)
    private val fetchTasksSizeByCategoryIdUseCases = FetchTasksSizeByCategoryIdUseCases(repository)

    var uiState by mutableStateOf(MainUiState())

    init {
        fetchAllTasksUseCases().onEach { tasks ->
            uiState = uiState.copy(tasks = tasks.map { task -> task.mapToTaskUi() })
        }.launchIn(viewModelScope)

        fetchAllTaskCategoryUseCases()
            .map {taskCategories ->
                val taskCategoryAndCount = taskCategories.map { category ->
                    val count = fetchTasksSizeByCategoryIdUseCases(categoryId = category.id)
                    Pair(category, count)
                }
                taskCategoryAndCount
            }
            .onEach { taskCategories ->
                uiState = uiState.copy(taskCategories = taskCategories)
            }.launchIn(viewModelScope)

    }

    fun onSelectItem(task: TaskUi, isSelected: Boolean) {
        val selectedTasks = uiState.selectedTasks.toMutableSet()
        if (isSelected) {
            selectedTasks.add(task)
        } else {
            selectedTasks.remove(task)
        }
        uiState = uiState.copy(selectedTasks = selectedTasks)
    }

    fun onRemoveSelectedItems() {
        val removedTasks = uiState.selectedTasks
        val taskIds = removedTasks.map { task: TaskUi -> task.id }
        removeTasksByIdsUseCase(taskIds)
    }

    fun onSelectAllItems() {
        val selectedTasks = uiState.selectedTasks.toMutableSet()
        selectedTasks.addAll(uiState.tasks)
        uiState = uiState.copy(selectedTasks = selectedTasks)
    }

    fun onUnselectAllItems() {
        val selectedTasks = uiState.selectedTasks.toMutableSet()
        selectedTasks.clear()
        uiState = uiState.copy(selectedTasks = selectedTasks)
    }
}