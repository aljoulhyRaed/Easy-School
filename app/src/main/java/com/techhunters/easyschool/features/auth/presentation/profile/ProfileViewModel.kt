package com.techhunters.easyschool.features.auth.presentation.profile

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.ext.idFromParameter
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.models.Manager
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService,
    private val authUseCases: AuthUseCases
) : EasySchoolViewModel(logService) {

    var user = mutableStateOf(User())
        private set

    lateinit var userId: String
        private set

    fun initialize(userId: String) {
        this.userId = userId
        launchCatching {
            user.value = authUseCases.getUserProfileData(userId) ?: User()
        }
    }

    fun getUserProfileData(): User {
        return user.value
    }

    fun isMe() = userId == authUseCases.getCurrentUserId()

}
