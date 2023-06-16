package com.techhunters.easyschool.features.teacher.settings

import BasicToolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.core.ext.card
import com.techhunters.easyschool.core.ext.spacer
import com.techhunters.easyschool.core.ui.composable.DangerousCardEditor
import com.techhunters.easyschool.core.ui.composable.DialogCancelButton
import com.techhunters.easyschool.core.ui.composable.DialogConfirmButton
import com.techhunters.easyschool.core.ui.composable.RegularCardEditor
import com.techhunters.easyschool.R.string as AppText
import com.techhunters.easyschool.R.drawable as AppIcon


@Composable
fun SettingsScreen(
    restartApp: (String) -> Unit,
    //openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
   // val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(AppText.settings)

        Spacer(modifier = Modifier.spacer())

        SignOutCard { viewModel.onSignOutClick(restartApp) }
        DeleteMyAccountCard { viewModel.onDeleteMyAccountClick(restartApp) }

        /*if (uiState.isAnonymousAccount) {
            RegularCardEditor(AppText.sign_in, AppIcon.ic_sign_in, "", Modifier.card()) {
                viewModel.onLoginClick(openScreen)
            }

            RegularCardEditor(AppText.create_account, AppIcon.ic_create_account, "", Modifier.card()) {
                viewModel.onSignUpClick(openScreen)
            }
        } else {
            SignOutCard { viewModel.onSignOutClick(restartApp) }
            DeleteMyAccountCard { viewModel.onDeleteMyAccountClick(restartApp) }
        }*/
    }
}

@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(AppText.sign_out, AppIcon.ic_exit, "", Modifier.card()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    DangerousCardEditor(
        AppText.delete_my_account,
        AppIcon.ic_delete_my_account,
        "",
        Modifier.card()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.delete_account_title)) },
            text = { Text(stringResource(AppText.delete_account_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.delete_my_account) {
                    deleteMyAccount()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
