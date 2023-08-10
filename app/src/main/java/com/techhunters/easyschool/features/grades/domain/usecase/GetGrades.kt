package com.techhunters.easyschool.features.grades.domain.usecase

import com.techhunters.easyschool.features.grades.domain.repository.GradesRepository

class GetGrades(private val gradesRepository: GradesRepository) {
    operator fun invoke() = gradesRepository.getGrades()
}