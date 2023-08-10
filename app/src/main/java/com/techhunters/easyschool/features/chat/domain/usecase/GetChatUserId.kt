package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class GetChatUserId(private val chatRepository: ChatRepository) {
    operator fun invoke()= chatRepository.getCurrentUserId()
}