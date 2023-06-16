package com.techhunters.easyschool.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.techhunters.easyschool.EasySchoolAppState
import com.techhunters.easyschool.core.EDIT_TASK_SCREEN
import com.techhunters.easyschool.core.LOGIN_SCREEN
import com.techhunters.easyschool.core.ROLE
import com.techhunters.easyschool.core.ROLE_ARG
import com.techhunters.easyschool.core.ROLE_SCREEN
import com.techhunters.easyschool.core.SETTINGS_SCREEN
import com.techhunters.easyschool.core.SPLASH_SCREEN
import com.techhunters.easyschool.core.TASKS_SCREEN
import com.techhunters.easyschool.core.TASK_DEFAULT_ID
import com.techhunters.easyschool.core.TASK_ID
import com.techhunters.easyschool.core.TASK_ID_ARG
import com.techhunters.easyschool.core.ui.screens.role.RoleScreen
import com.techhunters.easyschool.core.ui.screens.splash.SplashScreen
import com.techhunters.easyschool.features.auth.presentation.email_login.LoginScreen
import com.techhunters.easyschool.features.teacher.edit_task.EditTaskScreen
import com.techhunters.easyschool.features.teacher.settings.SettingsScreen
import com.techhunters.easyschool.features.teacher.tasks.presentation.TasksScreen

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
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            role = it.arguments?.getString(ROLE)!!
        )
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            //openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(TASKS_SCREEN) { TasksScreen(openScreen = { route -> appState.navigate(route) }) }

    composable(
        route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
        arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
    ) {
        EditTaskScreen(
            popUpScreen = { appState.popUp() },
            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID
        )

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

}
