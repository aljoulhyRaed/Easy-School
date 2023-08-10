package com.techhunters.easyschool.features.tasks.presentation.tasks

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.EDIT_TASK_SCREEN
import com.techhunters.easyschool.core.navigation.GRADE_DEFAULT_ID
import com.techhunters.easyschool.core.navigation.GRADE_ID
import com.techhunters.easyschool.core.navigation.SEGMENT_NAME
import com.techhunters.easyschool.core.navigation.SETTINGS_SCREEN
import com.techhunters.easyschool.core.navigation.SUBJECT_DEFAULT_ID
import com.techhunters.easyschool.core.navigation.SUBJECT_ID
import com.techhunters.easyschool.core.navigation.TASK_ID
import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.domain.usecase.TasksUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val tasksUseCases: TasksUseCases,
    private val configurationService: ConfigurationService
) : EasySchoolViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())
    // private var user = User()
   // val tasks = storeService.tasks

    /*fun getTasksUser():User{
        return user
    }*/
    fun loadTasks(gradeId: String, subId: String): Flow<List<Task>>? {
        if (gradeId != GRADE_DEFAULT_ID && subId != SUBJECT_DEFAULT_ID){
            return tasksUseCases.getTasks(gradeId, subId)
        }
        return null
    }
    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = TaskActionOption.getOptions(hasEditOption)
    }

    fun onTaskCheckChange(subId: String, gradeId: String,task: Task) {
        if (gradeId != GRADE_DEFAULT_ID && subId != SUBJECT_DEFAULT_ID)
        launchCatching { tasksUseCases.updateTask(gradeId, subId,task.copy(completed = !task.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onTaskActionClick(openScreen: (String) -> Unit, subId: String, gradeId: String, task: Task, action: String) {
        if (gradeId != GRADE_DEFAULT_ID && subId != SUBJECT_DEFAULT_ID)
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}?$GRADE_ID={${gradeId}}?$SUBJECT_ID={${subId}}")
            TaskActionOption.ToggleFlag -> onFlagTaskClick(gradeId,subId,task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(gradeId,subId,task)
        }
    }

    private fun onFlagTaskClick(gradeId: String, subId: String, task: Task) {
        if (gradeId != GRADE_DEFAULT_ID && subId != SUBJECT_DEFAULT_ID)
        launchCatching { tasksUseCases.updateTask(gradeId, subId, task.copy(flag = !task.flag)) }
    }

    private fun onDeleteTaskClick(gradeId: String, subId: String, task: Task) {
        if (gradeId != GRADE_DEFAULT_ID && subId != SUBJECT_DEFAULT_ID){
            launchCatching { tasksUseCases.deleteTask(gradeId, subId, task.id) }
        }

    }
}
