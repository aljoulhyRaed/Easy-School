package com.techhunters.easyschool

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.techhunters.easyschool.core.navigation.SPLASH_SCREEN
import com.techhunters.easyschool.core.navigation.easySchoolGraph
import com.techhunters.easyschool.core.ui.DrawerManager
import com.techhunters.easyschool.core.ui.composable.EasySchoolDrawer
import com.techhunters.easyschool.core.ui.composable.PermissionDialog
import com.techhunters.easyschool.core.ui.composable.RationaleDialog
import com.techhunters.easyschool.core.ui.snackbar.SnackBarManager
import com.techhunters.easyschool.core.ui.theme.EasySchoolTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EasySchoolApp() {
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

                val drawerOpen by DrawerManager.drawerShouldBeOpened
                    .collectAsStateWithLifecycle()

                if (drawerOpen) {
                    // Open drawer and reset state in VM.
                    LaunchedEffect(Unit) {
                        // wrap in try-finally to handle interruption whiles opening drawer
                        try {
                            appState.drawerState.open()
                        } finally {
                            DrawerManager.resetOpenDrawerAction()
                        }
                    }
                }

                // Intercepts back navigation when the drawer is open
                val scope = rememberCoroutineScope()
                if (appState.drawerState.isOpen) {
                    BackHandler {
                        scope.launch {
                            appState.drawerState.close()
                        }
                    }
                }

                EasySchoolDrawer(
                    clearAndNavigate = { route ->
                        appState.navigate(route)
                        scope.launch {
                            appState.drawerState.close()
                        }
                    },
                    popUp = {
                        appState.popUp()
                        scope.launch {
                            appState.drawerState.close()
                        }
                    },
                    drawerState = appState.drawerState
                ) {
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
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    snackBarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(
        snackBarHostState,
        drawerState,
        navController,
        snackBarManager,
        resources,
        coroutineScope
    ) {
        EasySchoolAppState(
            snackBarHostState,
            drawerState,
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