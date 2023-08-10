package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class InsertMessage(
    private val chatRepository: ChatRepository
){
    suspend operator fun invoke(
        gradeId: String,
        subId: String,
        message: ChatMessage,
    ) = chatRepository.insertMessage(
        gradeId,
        subId,
        message
    )
}