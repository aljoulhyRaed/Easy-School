package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class SendNotification(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(segmentName: String, messageContent: String) =
        chatRepository.sendNotification(segmentName, messageContent)
}