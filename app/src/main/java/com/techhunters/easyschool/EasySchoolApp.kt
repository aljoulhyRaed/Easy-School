package com.techhunters.easyschool

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.techhunters.easyschool.core.SPLASH_SCREEN
import com.techhunters.easyschool.core.navigation.easySchoolGraph
import com.techhunters.easyschool.core.ui.composable.PermissionDialog
import com.techhunters.easyschool.core.ui.composable.RationaleDialog
import com.techhunters.easyschool.core.ui.snackbar.SnackBarManager
import com.techhunters.easyschool.core.ui.theme.EasySchoolTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasySchoolApp(){
    EasySchoolTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermission()
        }
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackBarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackBarData ->
                            Snackbar(
                                snackBarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )

                        }

                    )
                }
            ) { innerPaddingModifier ->
                /* DestinationsNavHost(
                    navGraph = NavGraphs.root, modifier = Modifier.padding(
                        innerPaddingModifier
                    ),

                )*/
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    easySchoolGraph(appState)
                }
            }
        }


    }
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermission() {
    val permissionSate =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionSate.status.isGranted) {
        if (permissionSate.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionSate.launchPermissionRequest() }
    }
}

@Composable
fun rememberAppState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    //navigator: DestinationsNavigator,
    navController: NavHostController = rememberNavController(),
    snackBarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackBarHostState, navController, snackBarManager, resources, coroutineScope) {
        EasySchoolAppState(
            snackBarHostState,
            navController,
            snackBarManager,
            resources,
            coroutineScope
        )
    }


@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}