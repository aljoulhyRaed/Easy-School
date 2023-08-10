package com.techhunters.easyschool.features.auth.presentation.settings

import BasicToolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.core.ext.spacer
import com.techhunters.easyschool.features.auth.presentation.settings.composable.SignOutCard
import com.techhunters.easyschool.R.string as AppText


@Composable
fun SettingsScreen(
    restartApp: (String) -> Unit,
    //openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(AppText.settings)

        Spacer(modifier = Modifier.spacer())

        SignOutCard { viewModel.onSignOutClick(restartApp) }
    }
}




