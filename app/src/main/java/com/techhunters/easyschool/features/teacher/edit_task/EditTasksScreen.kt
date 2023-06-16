package com.techhunters.easyschool.features.teacher.edit_task

import ActionToolbar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.techhunters.easyschool.core.ext.card
import com.techhunters.easyschool.core.ext.fieldModifier
import com.techhunters.easyschool.core.ext.spacer
import com.techhunters.easyschool.core.ext.toolbarActions
import com.techhunters.easyschool.core.service.models.Priority
import com.techhunters.easyschool.core.ui.composable.BasicField
import com.techhunters.easyschool.core.ui.composable.CardSelector
import com.techhunters.easyschool.core.ui.composable.RegularCardEditor
import com.techhunters.easyschool.features.teacher.tasks.domain.Task
import java.time.LocalDate
import java.time.LocalTime
import com.techhunters.easyschool.R.string as AppText
import com.techhunters.easyschool.R.drawable as AppIcon


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTaskScreen(
    popUpScreen: () -> Unit,
    taskId: String,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val task by viewModel.task

    LaunchedEffect(Unit) { viewModel.initialize(taskId) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolbar(
            title = AppText.edit_task,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_check,
            endAction = { viewModel.onDoneClick(popUpScreen) }
        )

        Spacer(modifier = Modifier.spacer())

        val fieldModifier = Modifier.fieldModifier()
        BasicField(AppText.title, task.title, viewModel::onTitleChange, fieldModifier)
        BasicField(AppText.description, task.description, viewModel::onDescriptionChange, fieldModifier)
        BasicField(AppText.url, task.url, viewModel::onUrlChange, fieldModifier)

        Spacer(modifier = Modifier.spacer())
        CardEditors(task, viewModel::onDateChange, viewModel::onTimeChange)
        CardSelectors(task, viewModel::onPriorityChange, viewModel::onFlagToggle)

        Spacer(modifier = Modifier.spacer())
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditors(
    task: Task,
    onDateChange: (String) -> Unit,
    onTimeChange: (Int, Int) -> Unit
) {
   // val activity = LocalContext.current as ComponentActivity

    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val calendarState = rememberUseCaseState()

        CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate.value
        ) { newDate ->
            selectedDate.value = newDate
            onDateChange(selectedDate.value.toString())
        },
    )
    RegularCardEditor(AppText.date, AppIcon.ic_calendar, task.dueDate, Modifier.card()) {
        calendarState.show()
    }

    val selectedTime = remember { mutableStateOf(LocalTime.of(23, 20, 0)) }
    val clockState = rememberUseCaseState()
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedTime.value = LocalTime.of(hours, minutes, 0)
            val hour = selectedTime.value.hour
            val minute = selectedTime.value.minute
            onTimeChange(hour,minute)
        },
        config = ClockConfig(
            defaultTime = selectedTime.value,
            is24HourFormat = true
        )
    )

    RegularCardEditor(AppText.time, AppIcon.ic_clock, task.dueTime, Modifier.card()) {
        //showTimePicker(activity, onTimeChange)
        clockState.show()
    }
}

@Composable
private fun CardSelectors(
    task: Task,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(task.priority).name
    CardSelector(AppText.priority, Priority.getOptions(), prioritySelection, Modifier.card()) {
            newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
    CardSelector(AppText.flag, EditFlagOption.getOptions(), flagSelection, Modifier.card()) { newValue
        ->
        onFlagToggle(newValue)
    }
}

/*
private fun showDatePicker(activity: ComponentActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
    }
}

private fun showTimePicker(activity: ComponentActivity?, onTimeChange: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let {
        picker.show(it., picker.toString())
        picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
    }
}*/
