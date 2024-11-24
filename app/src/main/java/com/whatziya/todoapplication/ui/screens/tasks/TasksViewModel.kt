package com.whatziya.todoapplication.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.repository.TaskRepository
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import com.whatziya.todoapplication.preferences.PreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val preferencesProvider: PreferencesProvider
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
//            taskRepository.getAllTasks().onEach { tasks ->
//                _tasks.value = tasks
//            }.launchIn(viewModelScope)
           val task = remoteRepository.getAll().onEach { response ->
                _tasks.value = task.list.map { item ->
                    taskMapper.toUIModel(item)
                }
            }.launchIn(viewModelScope)
    }

    fun getAllTasks() {
        viewModelScope.launch {

                _tasks.value =  taskRepository.getAllTasks()
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

        }
    }

    fun updateTaskNet(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun deleteTask(task: String) {
        viewModelScope.launch {
        }
    }

    fun getShowCompletedPreference() = preferencesProvider.showCompleted

    fun setShowCompletedPreference(value: Boolean) {
        preferencesProvider.showCompleted = value
    }
}
