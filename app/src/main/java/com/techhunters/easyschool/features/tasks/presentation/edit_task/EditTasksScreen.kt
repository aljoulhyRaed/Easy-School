package com.techhunters.easyschool.features.tasks.presentation.edit_task

import ActionToolbar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.core.ext.fieldModifier
import com.techhunters.easyschool.core.ext.spacer
import com.techhunters.easyschool.core.ext.toolbarActions
import com.techhunters.easyschool.core.ui.composable.BasicField
import com.techhunters.easyschool.features.tasks.presentation.edit_task.composable.CardEditors
import com.techhunters.easyschool.features.tasks.presentation.edit_task.composable.CardSelectors
import com.techhunters.easyschool.R.string as AppText
import com.techhunters.easyschool.R.drawable as AppIcon


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTaskScreen(
    popUpScreen: () -> Unit,
    subId: String,
    gradeId: String,
    taskId: String,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val task by viewModel.task

    LaunchedEffect(Unit) { viewModel.initialize(subId, gradeId,taskId) }

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
            endAction = { viewModel.onDoneClick(gradeId, subId, popUpScreen) },
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

