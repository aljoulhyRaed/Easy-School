package com.techhunters.easyschool.features.chat.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.techhunters.easyschool.core.Constants
import com.techhunters.easyschool.core.service.models.Notification
import com.techhunters.easyschool.core.service.oneSignal.OneSignalService
import com.techhunters.easyschool.core.service.trace
import com.techhunters.easyschool.features.auth.data.AccountRepositoryImpl
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountRepository,
    private val oneSignalService: OneSignalService
) : ChatRepository {


    override suspend fun insertMessage(
        gradeId: String,
        subId: String,
        message: ChatMessage
    ): String =
        trace(ADD_MESSAGE_TRACE) {
            messagesCollection(subId, gradeId).add(message).await().id
        }

    override fun getCurrentUserId(): String {
        return auth.getCurrentUserId()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadMessages(gradeId: String, subId: String): Flow<List<ChatMessage>> {
        val messages: Flow<List<ChatMessage>> =
            messagesCollection(subId, gradeId).snapshots().mapLatest { messages ->
                messages.toObjects()
            }
        return messages
    }

    override fun getGradeStudents(gradeId: String): Flow<List<Student?>> {
        return currentCollection().whereEqualTo("classId", gradeId).snapshots().map { student-> student.toObjects() }
    }


    override suspend fun sendNotification(segmentName: String, message: String): Result<Unit> {
        val notification = Notification(
            appId = Constants.ONESIGNAL_APP_ID,
            contents = mapOf("en" to message),
            includedSegments = listOf(segmentName),
            data = mapOf("custom_key" to "custom_value")
        )
        return try {
            val response = oneSignalService.sendNotification(notification)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        "Notification send error: ${
                            response.errorBody()?.string()
                        }"
                    )
                )
                //Exception("Notification send error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Result.failure(e)
            //Result.Error(e)
        }
    }

    override suspend fun getAuthorImageUrl(): String {
      val userData= currentCollection().document(getCurrentUserId()).get().await()
       return userData.get("imageUrl") as String
    }

    private fun currentCollection(): CollectionReference =
        fireStore.collection(USER_COLLECTION)

    private fun messagesCollection(subId: String, gradeId: String): CollectionReference =
        fireStore.collection(SUBJECTS_COLLECTION).document(subId)
            .collection(CONVERSATION_COLLECTION).document(gradeId).collection(MESSAGES_COLLECTION)


    companion object {
        private const val USER_COLLECTION = "users"
        private const val CONVERSATION_COLLECTION = "conversations"
        private const val MESSAGES_COLLECTION = "messages"
        private const val SUBJECTS_COLLECTION = "subjects"
        private const val ADD_MESSAGE_TRACE = "addMessage"
    }
}