package com.techhunters.easyschool.features.subjects.domain.repository

import com.techhunters.easyschool.features.subjects.domain.model.Subject

interface SubjectsRepository {
    suspend fun determineSubjects(subjectsIds: List<String>): List<Subject?>
}