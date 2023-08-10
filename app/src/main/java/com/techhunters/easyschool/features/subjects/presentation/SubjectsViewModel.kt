package com.techhunters.easyschool.features.subjects.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.CHAT_SCREEN
import com.techhunters.easyschool.core.navigation.DEFAULT_LIST
import com.techhunters.easyschool.core.navigation.GRADE_ID
import com.techhunters.easyschool.core.navigation.SEGMENT_NAME
import com.techhunters.easyschool.core.navigation.SUBJECT_ID
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import com.techhunters.easyschool.features.grades.domain.usecase.GradesUseCases
import com.techhunters.easyschool.features.subjects.domain.model.Subject
import com.techhunters.easyschool.features.subjects.domain.usecase.SubjectsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel @Inject constructor(
    logService: LogService,
    private val subjectsUseCases: SubjectsUseCases,
) :
    EasySchoolViewModel(logService) {

     var subs : List<Subject?> = emptyList()
    fun initialize(subsIds: List<String>) {
        launchCatching {
            subs = subjectsUseCases.getSubjects(subsIds)
        }
    }

    fun getSubjects(): List<Subject?> {
       // val ls = mutableListOf<Subject>()
        return subs
    }

    fun onSubjectClick(openScreen: (String) -> Unit, gradeId: String, subId: String, segmentName: String) {
        openScreen("$CHAT_SCREEN?$GRADE_ID=${gradeId}$SUBJECT_ID=${subId}$SEGMENT_NAME=${segmentName}")
    }

}