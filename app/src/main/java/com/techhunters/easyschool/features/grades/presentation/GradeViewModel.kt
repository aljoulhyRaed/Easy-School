package com.techhunters.easyschool.features.grades.presentation

import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.GRADE_ID
import com.techhunters.easyschool.core.navigation.LIST
import com.techhunters.easyschool.core.navigation.SEGMENT_NAME
import com.techhunters.easyschool.core.navigation.SUBJECTS_SCREEN
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.grades.domain.model.Grade
import com.techhunters.easyschool.features.grades.domain.usecase.GradesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class GradeViewModel @Inject constructor(
    logService: LogService,
    private val gradesUseCases: GradesUseCases,
) : EasySchoolViewModel(logService) {

     var gradesList: Flow<List<Grade>> = gradesUseCases.getGrades()

    fun onGradeClick(
        openScreen: (String) -> Unit,
        subIds: List<String>,
        gradeId: String,
        segmentName: String
    )  {
        val subArray = subIds.toTypedArray() //as ArrayList<String>
        openScreen("$SUBJECTS_SCREEN?$LIST={${subArray}}?$GRADE_ID={${gradeId}}?$SEGMENT_NAME={${segmentName}}")
    }


}