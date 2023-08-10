package com.techhunters.easyschool.features.auth.presentation.profile.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techhunters.easyschool.R
import com.techhunters.easyschool.features.auth.domain.models.Teacher

@Composable
fun TeacherInfoFields(userData: Teacher, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        NameAndPosition(userData)

        ProfileProperty(stringResource(R.string.status), userData.status)
        ProfileProperty(stringResource(R.string.phoneNumber), userData.phoneNum)
        // Add a spacer that always shows part (320.dp) of the fields list regardless of the device,
        // in order to always leave some content at the top.
        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}