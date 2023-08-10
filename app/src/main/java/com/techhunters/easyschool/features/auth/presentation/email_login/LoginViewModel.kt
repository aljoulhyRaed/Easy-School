package com.techhunters.easyschool.features.auth.presentation.email_login

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.ext.isValidEmail
import com.techhunters.easyschool.core.navigation.GRADES_SCREEN
import com.techhunters.easyschool.core.navigation.SUBJECTS_SCREEN
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.core.ui.snackbar.SnackBarManager
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import com.techhunters.easyschool.R.string as AppText
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor
    (
    private val authUseCases: AuthUseCases,
    logService: LogService
) :
    EasySchoolViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    lateinit var role: String

    fun initialize(role: String) {
      this.role = role
    }


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    fun onSignInClick(clearAndNavigate: (String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }
        if (password.isBlank()) {
            SnackBarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            authUseCases.loginWithEmailAndPassword(email, password)
            when(role){
                "student" -> clearAndNavigate(SUBJECTS_SCREEN)
                "teacher" -> clearAndNavigate(GRADES_SCREEN)
                "manager" -> clearAndNavigate(GRADES_SCREEN)
            }
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            authUseCases.sendRecoveryEmail(email)
            SnackBarManager.showMessage(AppText.recovery_email_sent)
        }
    }

}