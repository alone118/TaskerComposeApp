package com.example.composetaskerapp.presentation.screens.add_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Destination(start = false)
@Composable
fun AddTask(
    navigator: DestinationsNavigator
) {
    val viewModel: AddTaskViewModel = viewModel()

    viewModel.navigateUpFlow.filterNotNull()
        .onEach {
        navigator.navigateUp()
    }.launchIn(rememberCoroutineScope())

    AddTaskScreen(
        uiState = viewModel.uiState,
        onSaveTask = viewModel::addNewTask,
        toastFlow = viewModel.toastFlow,
        onCancelClick = navigator::navigateUp,
        updateSelectedCategory = viewModel::updateSelectedCategory,
        updateTaskTitle = viewModel::updateTaskTitle,
        updateSelectedTime = viewModel::updateSelectedTime,
        updateSelectedData = viewModel::updateSelectedData
    )
}