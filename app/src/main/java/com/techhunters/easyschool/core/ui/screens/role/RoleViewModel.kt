package com.techhunters.easyschool.core.ui.screens.role

import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.LOGIN_SCREEN
import com.techhunters.easyschool.core.ROLE
import com.techhunters.easyschool.core.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(logService: LogService) :
    EasySchoolViewModel(logService) {

    private val studentRole = "student"
    private val teacherRole = "teacher"
    private val managerRole = "manager"

    fun onTeacherRoleSelected(open: (String) -> Unit) = open("$LOGIN_SCREEN?$ROLE=${teacherRole}")
    fun onStudentRoleSelected(open: (String) -> Unit) = open("$LOGIN_SCREEN?$ROLE=${studentRole}")
    fun onManagerRoleSelected(open: (String) -> Unit) = open("$LOGIN_SCREEN?$ROLE=${managerRole}")

}