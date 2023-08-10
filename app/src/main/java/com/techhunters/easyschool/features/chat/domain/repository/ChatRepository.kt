package com.techhunters.easyschool.features.chat.domain.repository

import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun insertMessage(
        gradeId: String,
        subId: String,
        message: ChatMessage,
        /*registerUUID: String,
        oneSignalUserId: String*/
    ): String

    fun getCurrentUserId(): String

     fun loadMessages(
        gradeId: String,
        subId: String
    ): Flow<List<ChatMessage>>

     fun getGradeStudents(gradeId: String): Flow<List<Student?>>

    suspend fun sendNotification(
        segmentName: String,
        message: String): Result<Unit>

    suspend fun getAuthorImageUrl(): String

   // suspend fun loadClassStudents(opponentUUID: String): Flow<Response<User>>
   // suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>
}