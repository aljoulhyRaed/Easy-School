package com.techhunters.easyschool.features.tasks.presentation.edit_task

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.TASK_DEFAULT_ID
import com.techhunters.easyschool.core.ext.idFromParameter
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.domain.usecase.TasksUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    logService: LogService,
    private val tasksUseCases: TasksUseCases,
) : EasySchoolViewModel(logService) {
    val task = mutableStateOf(Task())

    fun initialize(subId: String, gradeId:String, taskId: String) {
        launchCatching {
            if (taskId != TASK_DEFAULT_ID) {
                task.value = tasksUseCases.getTask(gradeId, subId, taskId.idFromParameter()) ?: Task()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        task.value = task.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task.value = task.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        task.value = task.value.copy(url = newValue)
    }


    fun onDateChange(newValue: String) {
        task.value = task.value.copy(dueDate = newValue)
    }


    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        task.value = task.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        task.value = task.value.copy(priority = newValue)
    }

    fun onDoneClick(gradeId: String, subId: String, popUpScreen: () -> Unit) {
        launchCatching {
            val editedTask = task.value
            if (editedTask.id.isBlank()) {
                tasksUseCases.saveTask(gradeId, subId,editedTask)
            } else {
                tasksUseCases.updateTask(gradeId, subId, editedTask)
            }
            popUpScreen()
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
