package com.techhunters.easyschool.features.teacher.tasks.presentation

import ActionToolbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.core.ext.smallSpacer
import com.techhunters.easyschool.core.ext.toolbarActions
import com.techhunters.easyschool.R.string as AppText
import com.techhunters.easyschool.R.drawable as AppIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Add",
                )
            }
        }
    ) { innerPadding ->
        val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
        val options by viewModel.options

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.tasks,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { viewModel.onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(tasks.value, key = { it.id }) { taskItem ->
                    TaskItem(
                        task = taskItem,
                        options = options,
                        onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
                        onActionClick = { action ->
                            viewModel.onTaskActionClick(
                                openScreen,
                                taskItem,
                                action
                            )
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}