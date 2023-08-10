package com.techhunters.easyschool.features.tasks.presentation.edit_task.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ext.card
import com.techhunters.easyschool.core.ui.composable.RegularCardEditor
import com.techhunters.easyschool.features.tasks.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardEditors(
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
    RegularCardEditor(R.string.date, R.drawable.ic_calendar, task.dueDate, Modifier.card()) {
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

    RegularCardEditor(R.string.time, R.drawable.ic_clock, task.dueTime, Modifier.card()) {
        //showTimePicker(activity, onTimeChange)
        clockState.show()
    }
}