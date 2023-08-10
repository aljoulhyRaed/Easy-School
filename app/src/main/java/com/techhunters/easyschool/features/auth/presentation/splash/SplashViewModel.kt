package com.techhunters.easyschool.features.auth.presentation.splash

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.GRADES_SCREEN
import com.techhunters.easyschool.core.navigation.ROLE_SCREEN
import com.techhunters.easyschool.core.navigation.SPLASH_SCREEN
import com.techhunters.easyschool.core.navigation.SUBJECTS_SCREEN
import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.models.Manager
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val authUseCases: AuthUseCases,
    logService: LogService
) : EasySchoolViewModel(logService){

    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        //TODO:direct users according to their roles
        showError.value = false
        if (authUseCases.hasUser()) {
            openAndPopUp(GRADES_SCREEN, SPLASH_SCREEN)
            launchCatching {
               when(authUseCases.getCurrentUser()){
                    is Student -> openAndPopUp(SUBJECTS_SCREEN, SPLASH_SCREEN)
                    is Teacher -> openAndPopUp(GRADES_SCREEN, SPLASH_SCREEN)
                    is Manager -> openAndPopUp(GRADES_SCREEN, SPLASH_SCREEN)
                }
            }
        }
       else openAndPopUp(ROLE_SCREEN, SPLASH_SCREEN) //createAnonymousAccount(openAndPopUp)
    }
}