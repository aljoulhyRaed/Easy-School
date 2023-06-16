package com.techhunters.easyschool.features.teacher.tasks.presentation

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.EDIT_TASK_SCREEN
import com.techhunters.easyschool.core.SETTINGS_SCREEN
import com.techhunters.easyschool.core.TASK_ID
import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.DataStorageService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.teacher.tasks.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storeService: DataStorageService,
    private val configurationService: ConfigurationService
) : EasySchoolViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())

    val tasks = storeService.tasks

    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = TaskActionOption.getOptions(hasEditOption)
    }

    fun onTaskCheckChange(task: Task) {
        launchCatching { storeService.update(task.copy(completed = !task.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
            TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
        }
    }

    private fun onFlagTaskClick(task: Task) {
        launchCatching { storeService.update(task.copy(flag = !task.flag)) }
    }

    private fun onDeleteTaskClick(task: Task) {
        launchCatching { storeService.delete(task.id) }
    }
}
