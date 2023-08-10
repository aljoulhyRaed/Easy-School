package com.techhunters.easyschool.core.ui.composable

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable

@Composable
fun EasySchoolDrawer(
    drawerState: DrawerState,
    clearAndNavigate: (String) -> Unit,
    popUp: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                EasySchoolDrawerContent(
                    open = clearAndNavigate,
                    popUp = popUp
                )
            }
        },
        content = content
    )
}