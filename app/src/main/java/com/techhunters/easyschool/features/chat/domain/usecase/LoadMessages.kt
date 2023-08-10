package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class LoadMessages(
    private val chatRepository: ChatRepository
) {
     operator fun invoke(
        gradeId: String,
        subId: String
    ) = chatRepository.loadMessages(
        gradeId,
        subId
    )
}