package com.techhunters.easyschool.features.chat.presentation.conversation

import androidx.compose.runtime.mutableStateOf
import com.techhunters.easyschool.EasySchoolViewModel
import com.techhunters.easyschool.core.navigation.GRADE_DEFAULT_ID
import com.techhunters.easyschool.core.navigation.PROFILE_SCREEN
import com.techhunters.easyschool.core.navigation.USER_DEFAULT_ID
import com.techhunters.easyschool.core.navigation.USER_ID
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import com.techhunters.easyschool.features.chat.domain.usecase.ChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    logService: LogService,
    private val chatUseCases: ChatUseCases
    // initialMessages: List<Message>
) : EasySchoolViewModel(logService) {

    private val authorImageUrl = mutableStateOf("")

    init {
        launchCatching {
            authorImageUrl.value = chatUseCases.getAuthorImageUrl()
        }
    }

    fun getAuthorImageUrl():String{
        return authorImageUrl.value
    }


    fun getCurrentUserId(): String {
        return chatUseCases.getChatUserId()

    }

    fun getMessages(gradeId: String, subId: String): Flow<List<ChatMessage>> {
        return chatUseCases.loadMessages(gradeId = gradeId, subId = subId)
    }

    fun navigateToProfile(open: (String) -> Unit, userId: String) {
        if(userId!= USER_DEFAULT_ID)
        open("$PROFILE_SCREEN$USER_ID=${userId}")
    }

    fun addMessage(msg: ChatMessage, gradeId: String, subId: String, segmentName: String) {
        launchCatching {
            if (msg.id.isBlank() && gradeId!= GRADE_DEFAULT_ID) {
                chatUseCases.insertMessage(gradeId, subId, msg)
                chatUseCases.sendNotification(segmentName,msg.content)
            }
        }

    }

}