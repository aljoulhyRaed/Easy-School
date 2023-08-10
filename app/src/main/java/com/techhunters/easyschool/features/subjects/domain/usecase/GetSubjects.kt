package com.techhunters.easyschool.features.subjects.domain.usecase

import com.techhunters.easyschool.features.subjects.domain.repository.SubjectsRepository

class GetSubjects(private val subjectsRepository: SubjectsRepository) {
    suspend operator fun invoke(subjectsIds: List<String>) =
        subjectsRepository.determineSubjects(subjectsIds)
}