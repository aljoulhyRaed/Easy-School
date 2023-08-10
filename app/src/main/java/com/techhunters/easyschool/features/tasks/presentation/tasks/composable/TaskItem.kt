package com.techhunters.easyschool.features.tasks.presentation.tasks.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techhunters.easyschool.core.ext.contextMenu
import com.techhunters.easyschool.core.ext.hasDueDate
import com.techhunters.easyschool.core.ext.hasDueTime
import com.techhunters.easyschool.core.ui.composable.DropdownContextMenu
import com.techhunters.easyschool.core.ui.theme.black0
import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.R.drawable as AppIcon
import java.lang.StringBuilder


@Composable
fun TaskItem(
    task: Task,
    options: List<String>,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.labelSmall)
                //TODO: check the usage of this function
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary ) {
                    Text(text = getDueDateAndTime(task), fontSize = 12.sp)
                }

            }

            if (task.flag) {
                Icon(
                    painter = painterResource(AppIcon.ic_flag),
                    tint = black0,
                    contentDescription = "Flag"
                )
            }

            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        }
    }
}

private fun getDueDateAndTime(task: Task): String {
    val stringBuilder = StringBuilder("")

    if (task.hasDueDate()) {
        stringBuilder.append(task.dueDate)
        stringBuilder.append(" ")
    }

    if (task.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(task.dueTime)
    }

    return stringBuilder.toString()
}
