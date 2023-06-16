package com.techhunters.easyschool.features.teacher.edit_task

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.TASK_DEFAULT_ID
import com.techhunters.easyschool.core.ext.idFromParameter
import com.techhunters.easyschool.core.service.DataStorageService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.teacher.tasks.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    logService: LogService,
    private val storeService: DataStorageService,
) : EasySchoolViewModel(logService) {
    val task = mutableStateOf(Task())
    /*@RequiresApi(Build.VERSION_CODES.O)
    val selectedDate = mutableStateOf<LocalDate?>(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedTime = mutableStateOf(LocalTime.of(23, 20, 0))*/

    fun initialize(taskId: String) {
        launchCatching {
            if (taskId != TASK_DEFAULT_ID) {
                task.value = storeService.getTask(taskId.idFromParameter()) ?: Task()
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

    /*fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        task.value = task.value.copy(dueDate = newDueDate)
    }*/

    fun onDateChange(newValue: String) {
        task.value = task.value.copy(dueDate = newValue)
    }

    /*fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }*/

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

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedTask = task.value
            if (editedTask.id.isBlank()) {
                storeService.save(editedTask)
            } else {
                storeService.update(editedTask)
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
