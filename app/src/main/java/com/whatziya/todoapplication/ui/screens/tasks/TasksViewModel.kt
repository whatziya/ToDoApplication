package com.whatziya.todoapplication.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.repository.TaskRepository
import com.whatziya.todoapplication.domain.mapper.toDTO
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
        taskRepository.getAll().onEach { response ->
            _tasks.value = response
        }.launchIn(viewModelScope)
    }

    fun getAll() {
        viewModelScope.launch {
            taskRepository.getAll().onEach { response ->
                _tasks.value = response
            }
        }
    }

    fun update(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = true)
            taskRepository.update(updatedTask.id, TaskReqDto(updatedTask.toDTO()))
        }
    }

    fun delete(task: String) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    fun getShowCompletedPreference() = preferencesProvider.showCompleted

    fun setShowCompletedPreference(value: Boolean) {
        preferencesProvider.showCompleted = value
    }
}
