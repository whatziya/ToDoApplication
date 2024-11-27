package com.whatziya.todoapplication.ui.screens.task

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.repository.TaskRepository
import com.whatziya.todoapplication.domain.mapper.toDTO
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

    private var importanceLevel = mutableIntStateOf(0)

    fun getTaskById(id: String) {
        viewModelScope.launch {
            if (id == DEFAULT_TASK_ID) {
                _task.value = TaskEntity(
                    id = UUID.randomUUID().toString(),
                    text = "",
                    importance = 0,
                    deadline = null,
                    isCompleted = false,
                    createdAt = System.currentTimeMillis(),
                    modifiedAt = System.currentTimeMillis()
                )
            } else {
                _task.value = repository._getTaskById(id)
            }
        }
    }

    fun insertTask() = viewModelScope.launch {
        repository.add(TaskReqDto(_task.value!!.toDTO()))
    }

    fun updateTask() = viewModelScope.launch {
        //this function must be varied to any case
        updateModifiedAt()
        repository.update(_task.value!!.id, TaskReqDto(_task.value!!.toDTO()))
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        repository.delete(id)
    }

    fun updateText(text: String) {
        _task.value = task.value?.copy(text = text)
    }

    fun updateImportance(importance: Int) {
        _task.value = task.value?.copy(importance = importance)
        importanceLevel.intValue = importance
    }

    fun updateDeadline(deadline: Long?) {
        _task.value = task.value?.copy(deadline = deadline)
    }

    private fun updateModifiedAt() {
        _task.value = task.value?.copy(modifiedAt = System.currentTimeMillis())
    }

}