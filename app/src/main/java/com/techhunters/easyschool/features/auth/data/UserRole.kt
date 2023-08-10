package com.techhunters.easyschool.features.auth.data

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow


object UserRole {
    private var _currentUserRole = MutableStateFlow("")
    var currentUserRole = _currentUserRole.value

    fun setUserRole(role :String) {
        _currentUserRole.value = role
    }

}