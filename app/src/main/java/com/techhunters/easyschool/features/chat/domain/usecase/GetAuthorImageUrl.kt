package com.techhunters.easyschool.features.chat.domain.usecase

import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository

class GetAuthorImageUrl(private val chatRepository: ChatRepository) {
    suspend operator fun invoke()=
        chatRepository.getAuthorImageUrl()
}