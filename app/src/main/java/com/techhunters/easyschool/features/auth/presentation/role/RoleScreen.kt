package com.techhunters.easyschool.features.auth.presentation.role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.core.ui.composable.IconTextButton
import com.techhunters.easyschool.R.string as AppText


@Composable
fun RoleScreen(
    modifier: Modifier = Modifier,
    viewModel: RoleViewModel = hiltViewModel(),
    open: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //TODO : add an image with caption

        IconTextButton(text = AppText.student,
            modifier = modifier,
        action = {viewModel.onStudentRoleSelected(open)})

        Spacer(modifier = modifier.height(20.dp))

        IconTextButton(text = AppText.teacher,
            modifier = modifier,
            action = {viewModel.onTeacherRoleSelected(open)})

        Spacer(modifier = modifier.height(20.dp))

        IconTextButton(text = AppText.manager,
            modifier = modifier,
            action = {viewModel.onManagerRoleSelected(open)})


    }

}