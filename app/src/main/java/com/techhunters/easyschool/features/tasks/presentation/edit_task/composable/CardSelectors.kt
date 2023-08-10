package com.techhunters.easyschool.features.tasks.presentation.edit_task.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ext.card
import com.techhunters.easyschool.core.service.models.Priority
import com.techhunters.easyschool.core.ui.composable.CardSelector
import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.presentation.edit_task.EditFlagOption

@Composable
fun CardSelectors(
    task: Task,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(task.priority).name
    CardSelector(R.string.priority, Priority.getOptions(), prioritySelection, Modifier.card()) {
            newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
    CardSelector(
        R.string.flag,
        EditFlagOption.getOptions(), flagSelection, Modifier.card()) { newValue
        ->
        onFlagToggle(newValue)
    }
}
