package com.techhunters.easyschool.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.techhunters.easyschool.EasySchoolAppState
import com.techhunters.easyschool.features.auth.presentation.role.RoleScreen
import com.techhunters.easyschool.features.auth.presentation.splash.SplashScreen
import com.techhunters.easyschool.features.auth.presentation.email_login.LoginScreen
import com.techhunters.easyschool.features.chat.presentation.conversation.ConversationScreen
import com.techhunters.easyschool.features.auth.presentation.profile.ProfileScreen
import com.techhunters.easyschool.features.tasks.presentation.edit_task.EditTaskScreen
import com.techhunters.easyschool.features.grades.presentation.GradesScreen
import com.techhunters.easyschool.features.auth.presentation.settings.SettingsScreen
import com.techhunters.easyschool.features.subjects.presentation.SubjectsScreens
import com.techhunters.easyschool.features.tasks.presentation.tasks.TasksScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.easySchoolGraph(appState: EasySchoolAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(ROLE_SCREEN) {
        RoleScreen { route -> appState.navigate(route) }
    }

    composable(
        route = "$LOGIN_SCREEN$ROLE_ARG",
        arguments = listOf(navArgument(ROLE) {}),
    ) {
        LoginScreen(
            clearAndNavigate = { route -> appState.clearAndNavigate(route) },
            role = it.arguments?.getString(ROLE)!!
        )
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            //openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$TASKS_SCREEN$GRADE_ID_ARG$SUBJECT_ID_ARG",
        arguments = listOf(
            navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID },
            navArgument(GRADE_ID) { defaultValue = GRADE_DEFAULT_ID },
        )
    ) {
        TasksScreen(
            gradeId = it.arguments?.getString(GRADE_ID) ?: GRADE_DEFAULT_ID,
            subId = it.arguments?.getString(SUBJECT_ID) ?: SUBJECT_DEFAULT_ID,
            openScreen = { route -> appState.navigate(route) })
    }

    composable(
        route = "$EDIT_TASK_SCREEN$TASK_ID_ARG$GRADE_ID_ARG$SUBJECT_ID_ARG",
        arguments = listOf(
            navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID },
            navArgument(GRADE_ID) { defaultValue = GRADE_DEFAULT_ID },
            navArgument(SUBJECT_ID) { defaultValue = SUBJECT_DEFAULT_ID }
        )
    ) {
        EditTaskScreen(
            popUpScreen = { appState.popUp() },
            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID,
            gradeId = it.arguments?.getString(GRADE_ID) ?: GRADE_DEFAULT_ID,
            subId = it.arguments?.getString(SUBJECT_ID) ?: SUBJECT_DEFAULT_ID,
        )


    }

    composable(GRADES_SCREEN) {
        GradesScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(
        route = "$SUBJECTS_SCREEN$LIST_ID_ARG$GRADE_ID_ARG$SEGMENT_NAME_ARG",
        arguments = listOf(
            navArgument(LIST) {  },
            navArgument(GRADE_ID) { defaultValue = GRADE_DEFAULT_ID },
            navArgument(SEGMENT_NAME) { defaultValue = SEGMENT_DEFAULT_ID }
        ),

        ) {
        val gradeId = it.arguments?.getString(GRADE_ID) ?: GRADE_DEFAULT_ID
        val segmentName = it.arguments?.getString(SEGMENT_NAME) ?: SEGMENT_DEFAULT_ID
        val subIds = it.arguments?.getStringArrayList(LIST) ?: DEFAULT_LIST
        SubjectsScreens(
            open = { route -> appState.navigate(route) },
            subIds = subIds,
            gradeId = gradeId,
            segmentName = segmentName
        )
    }

    composable(
        route = "$CHAT_SCREEN$GRADE_ID_ARG$SUBJECT_ID_ARG$SEGMENT_NAME_ARG",
        arguments = listOf(
            navArgument(GRADE_ID) { defaultValue = GRADE_DEFAULT_ID },
            navArgument(SUBJECT_ID) { defaultValue = SUBJECT_DEFAULT_ID },
            navArgument(SEGMENT_NAME) { defaultValue = SEGMENT_DEFAULT_ID }
        )
    ) {
        ConversationScreen(
            navigateToProfile = { route -> appState.navigate(route) },
            gradeId = it.arguments?.getString(GRADE_ID) ?: GRADE_DEFAULT_ID,
            subId = it.arguments?.getString(SUBJECT_ID) ?: SUBJECT_DEFAULT_ID,
            segmentName = it.arguments?.getString(SEGMENT_NAME) ?: SEGMENT_DEFAULT_ID
        )
    }

    composable(
        route = "$PROFILE_SCREEN$USER_ID_ARG",
        arguments = listOf(
            navArgument(USER_ID) { defaultValue = USER_DEFAULT_ID },
        )
    ) {
        val userId = it.arguments?.getString(USER_ID) ?: USER_DEFAULT_ID
        ProfileScreen(
            open = { route -> appState.navigate(route) },
            userId = userId
        )
    }

    /* composable(PHONE_LOGIN_SCREEN) {
               PhoneNumberLoginScreen(
                   open = { route -> appState.navigate(route) },
                   onClick = {},
                   phone = "phone",
                   onPhoneChange = {},
                   onDone = {}
               )
           }
  
            composable(LOGIN_SCREEN) {
                LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
            }
  
            composable(SIGN_UP_SCREEN) {
                SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
            }
  
            }*/

}


