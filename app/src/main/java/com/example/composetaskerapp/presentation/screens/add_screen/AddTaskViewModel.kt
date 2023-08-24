package com.example.composetaskerapp.presentation.screens.add_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetaskerapp.R
import com.example.composetaskerapp.data.repository.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.data.repository.TaskRepositoryImpl
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.repository.TaskRepository
import com.example.composetaskerapp.domain.usecases.AddNewTaskUseCases
import com.example.composetaskerapp.domain.usecases.FetchAllTaskCategoryUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID

class AddTaskViewModel : ViewModel() {

    private val repository: TaskRepository = TaskRepositoryImpl()
    private val taskCategoryRepository = TaskCategoryRepositoryImpl()

    private val addNewTaskUseCases = AddNewTaskUseCases(repository)
    private val fetchAllTaskCategoryUseCases = FetchAllTaskCategoryUseCases(taskCategoryRepository)

    var uiState by mutableStateOf(AddTaskScreenUiState())

    private val _toastFlow = MutableSharedFlow<Int>(1)
    val toastFlow = _toastFlow.asSharedFlow()

    private val _navigateUpFlow = MutableStateFlow<Unit?>(null)
    val navigateUpFlow = _navigateUpFlow.asStateFlow()

    init {
        fetchAllTaskCategoryUseCases().onEach { taskCategories ->
            uiState = uiState.copy(tasksCategories = taskCategories)
        }.launchIn(viewModelScope)
    }

    fun updateSelectedData(data: String) {
        uiState = uiState.copy(selectedDate = data)
    }

    fun updateSelectedTime(time: String) {
        uiState = uiState.copy(selectedTime = time)
    }

    fun updateSelectedCategory(category: TaskCategory) {
        uiState = uiState.copy(selectedCategory = category)
    }

    fun updateTaskTitle(title: String) {
        uiState = uiState.copy(title = title)
    }


    fun addNewTask() {
        if (uiState.title.isNullOrBlank()) {
            _toastFlow.tryEmit(R.string.error_empty_title)
            return
        }
        if (uiState.selectedDate.isNullOrBlank()) {
            _toastFlow.tryEmit(R.string.error_data)
            return
        }
        if (uiState.selectedTime.isNullOrBlank()) {
            _toastFlow.tryEmit(R.string.error_time)
        }
        if (uiState.selectedCategory == null) {
            _toastFlow.tryEmit(R.string.error_category)
            return
        }
        var task = Task(
            id = UUID.randomUUID().toString(),
            time = uiState.selectedTime!!,
            data = uiState.selectedDate!!,
            categoryId = uiState.selectedCategory!!.id,
            title = uiState.title!!,
            categoryColor = uiState.selectedCategory!!.colorCode
        )
        addNewTaskUseCases(task)
        uiState = AddTaskScreenUiState()
        _navigateUpFlow.tryEmit(Unit)
    }
}