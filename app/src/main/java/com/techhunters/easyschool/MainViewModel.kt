package com.techhunters.easyschool

import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.models.UserStatus
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    logService: LogService,
    private val authUseCases: AuthUseCases
): EasySchoolViewModel(logService) {
    fun setUserStatus(userStatus: UserStatus){
        launchCatching {
            authUseCases.setUserStatus(userStatus)
        }
    }
}