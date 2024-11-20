package com.whatziya.todoapplication.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.repository.local.task.LocalRepository
import com.whatziya.todoapplication.domain.usecase.DeleteUseCase
import com.whatziya.todoapplication.domain.usecase.GetAllUseCase
import com.whatziya.todoapplication.domain.usecase.UpdateUseCase
import com.whatziya.todoapplication.preferences.PreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: LocalRepository,
    private val getAllUseCase: GetAllUseCase,
    private val updateUseCase: UpdateUseCase,
    private val deleteTaskUseCase: DeleteUseCase,
    private val preferencesProvider: PreferencesProvider
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect { tasks ->
                _tasks.value = tasks
            }
            println(getAllUseCase())
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect { tasks ->
                _tasks.value = tasks
            }
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = true)
            taskRepository.updateTask(updatedTask)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = getAllUseCase.invoke()
        }
    }

    fun updateTaskNet(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            updateUseCase.invoke(
                updatedTask,
                taskId = updatedTask.id
            )
            loadTasks()
        }
    }

    fun deleteTask(task: String) {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(task)
            loadTasks()
        }
    }

    fun getShowCompletedPreference() = preferencesProvider.showCompleted

    fun setShowCompletedPreference(value: Boolean) {
        preferencesProvider.showCompleted = value
    }
}
