package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class GetGradeStudents(private val chatRepository: ChatRepository) {
    operator fun invoke(gradeId: String)=
        chatRepository.getGradeStudents(gradeId)
}