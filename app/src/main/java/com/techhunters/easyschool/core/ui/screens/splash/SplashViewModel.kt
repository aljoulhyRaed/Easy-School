package com.techhunters.easyschool.core.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.LOGIN_SCREEN
import com.techhunters.easyschool.core.ROLE_SCREEN
import com.techhunters.easyschool.core.SPLASH_SCREEN
import com.techhunters.easyschool.core.TASKS_SCREEN
import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : EasySchoolViewModel(logService){

    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        //TODO:direct users according to their roles
        showError.value = false
        if (accountService.hasUser) openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
       else openAndPopUp(ROLE_SCREEN, SPLASH_SCREEN) //createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackBar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
        }
    }

}