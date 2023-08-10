package com.techhunters.easyschool.features.auth.presentation.settings

import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.SPLASH_SCREEN
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val authUseCases: AuthUseCases,
) : EasySchoolViewModel(logService) {
    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            authUseCases.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }
}
