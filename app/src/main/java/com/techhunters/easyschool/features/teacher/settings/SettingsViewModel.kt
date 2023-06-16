package com.techhunters.easyschool.features.teacher.settings

import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.LOGIN_SCREEN
import com.techhunters.easyschool.core.SIGN_UP_SCREEN
import com.techhunters.easyschool.core.SPLASH_SCREEN
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    //private val storageService: StorageService
) : EasySchoolViewModel(logService) {
    //val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}
