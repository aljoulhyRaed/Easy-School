package com.techhunters.easyschool

import android.content.res.Resources
import androidx.compose.material3.DrawerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.techhunters.easyschool.core.ui.snackbar.SnackBarManager
import com.techhunters.easyschool.core.ui.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class EasySchoolAppState(
    val snackBarHostState: SnackbarHostState,
    val drawerState: DrawerState,
    val navController: NavHostController,
    // val navigator: DestinationsNavigator,
    private val snackBarManager: SnackBarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackBarManager.snackBarMessages.filterNotNull().collect{snackBarMessage ->
                val txt = snackBarMessage.toMessage(resources)
                snackBarHostState.showSnackbar(txt)
            }
        }
    }


    fun popUp() {
        navController.popBackStack()
        // navigator.popBackStack()
    }

    fun navigate(route:String) {
        // navigator.navigate(direction) {launchSingleTop = true}
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        /* navigator.navigate(direction){
             launchSingleTop =true
             popUpTo(popUp)
         }*/
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        /*navigator.navigate(direction){
            launchSingleTop = true
            popUpTo(0) {inclusive = true}
        }*/
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

}