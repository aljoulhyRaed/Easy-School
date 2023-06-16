package com.techhunters.easyschool.core.ui.composable


import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/*
AssistChip(
            modifier = modifier
                .height(50.dp)
                .width(200.dp),
            label = { Text(text = "مدرس") },
            onClick = {
                viewModel.onTeacherRoleSelected(open)
            },
            colors = AssistChipDefaults.assistChipColors(
                leadingIconContentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.surface
            ),
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = null)
            }
        )
 */

@Composable
fun IconTextButton(@StringRes text: Int, modifier: Modifier=Modifier, action: () -> Unit){
    Button(
        onClick = action,
        modifier = modifier
            .height(60.dp)
            .width(300.dp),
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp,MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = text), style = MaterialTheme.typography.labelMedium)
            Icon(imageVector = Icons.Filled.Person, contentDescription = null)
        }
    }
}

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) { Text(text = stringResource(text)) }
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = stringResource(text))
    }
}