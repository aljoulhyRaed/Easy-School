package com.techhunters.easyschool.features.grades.domain.repository

import com.techhunters.easyschool.features.grades.domain.model.Grade
import kotlinx.coroutines.flow.Flow

interface GradesRepository {
    fun getGrades(): Flow<List<Grade>>
   suspend fun  getStudentGrade(gradeId: String): Grade?
}