package com.techhunters.easyschool.features.chat.domain.usecase

data class ChatUseCases(
    val insertMessage: InsertMessage,
    val loadMessages: LoadMessages,
    val getGradeStudents: GetGradeStudents,
    val getAuthorImageUrl: GetAuthorImageUrl,
    val getChatUserId: GetChatUserId,
    val sendNotification: SendNotification
)