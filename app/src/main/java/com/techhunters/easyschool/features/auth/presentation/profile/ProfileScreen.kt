package com.techhunters.easyschool.features.auth.presentation.profile

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.features.auth.domain.models.Manager
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.core.ui.composable.FunctionalityNotAvailablePopup
import com.techhunters.easyschool.features.auth.presentation.profile.composable.ManagerInfoFields
import com.techhunters.easyschool.features.auth.presentation.profile.composable.ProfileError
import com.techhunters.easyschool.features.auth.presentation.profile.composable.ProfileFab
import com.techhunters.easyschool.features.auth.presentation.profile.composable.ProfileHeader
import com.techhunters.easyschool.features.auth.presentation.profile.composable.StudentInfoFields
import com.techhunters.easyschool.features.auth.presentation.profile.composable.TeacherInfoFields


@Composable
fun ProfileScreen(
    open: (String) -> Unit,
    userId: String,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
    LaunchedEffect(Unit) { profileViewModel.initialize(userId) }


    var functionalityNotAvailablePopupShown by remember { mutableStateOf(false) }
    if (functionalityNotAvailablePopupShown) {
        FunctionalityNotAvailablePopup { functionalityNotAvailablePopupShown = false }
    }

    val scrollState = rememberScrollState()
    val userData = profileViewModel.getUserProfileData()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollInteropConnection)
            .systemBarsPadding()
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {
                ProfileHeader(
                    onClicked = { functionalityNotAvailablePopupShown = true },
                    scrollState,
                    userData,
                    this@BoxWithConstraints.maxHeight
                )
                when (userData) {
                    is Teacher -> TeacherInfoFields(userData, this@BoxWithConstraints.maxHeight)
                    is Student -> StudentInfoFields(userData, this@BoxWithConstraints.maxHeight)
                    is Manager -> ManagerInfoFields(userData, this@BoxWithConstraints.maxHeight)
                    else -> ProfileError()
                }

            }

        }

        val fabExtended by remember { derivedStateOf { scrollState.value == 0 } }
        ProfileFab(
            extended = fabExtended,
            userIsMe = profileViewModel.isMe(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                // Offsets the FAB to compensate for CoordinatorLayout collapsing behaviour
                .offset(y = ((-100).dp)),
            onFabClicked = { functionalityNotAvailablePopupShown = true }
        )
    }
}
