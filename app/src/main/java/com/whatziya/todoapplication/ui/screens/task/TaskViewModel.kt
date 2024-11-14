package com.whatziya.todoapplication.ui.screens.task

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.navigation.DEFAULT_TASK_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _task = MutableStateFlow<TaskEntity?>(null)
    val task: StateFlow<TaskEntity?> = _task

    var importanceLevel = mutableIntStateOf(0)

    fun getTaskById(id: String) {
        viewModelScope.launch {
            if (id == DEFAULT_TASK_ID) {
                _task.value = TaskEntity(
                    id = UUID.randomUUID().toString(),
                    text = "",
                    importance = 0,
                    deadline = System.currentTimeMillis(),
                    isCompleted = false,
                    createdAt = System.currentTimeMillis(),
                    modifiedAt = null
                )
            } else {
                repository.getTaskById(id).collect { task ->
                    _task.value = task
                }
            }
        }
    }

    fun insertTask() = viewModelScope.launch {
        repository.insertTask(task.value!!)
    }

    fun updateTask() = viewModelScope.launch {
        //this function must be varied to any case
        repository.updateTask(task.value!!)
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        repository.deleteTaskById(id)
    }

    fun updateText(text: String) {
        _task.value = task.value?.copy(text = text)
    }

    fun updateImportance(importance: Int) {
        _task.value = task.value?.copy(importance = importance)
        importanceLevel.intValue = importance
    }

    fun updateDeadline(deadline : Long){
        _task.value = task.value?.copy(deadline = deadline)
    }

}