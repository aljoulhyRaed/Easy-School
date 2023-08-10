package com.techhunters.easyschool.features.chat.domain.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ChatMessage(
    @DocumentId val id: String = "",
    val authorId: String = "",
    val content: String = "",
    val timestamp: Timestamp = Timestamp.now(),
   // val image: Int? = null,
)

/*
data class MessageEntity(
    @DocumentId val id : String = "",
    val authorId: String ="",
    val content: String="",
    val timestamp: Timestamp = Timestamp.now(),
    val image: Int? = null,
    val authorImage: String="",
    //Int = R.drawable.ic_sign_in //if (author == "me") R.drawable.ali else R.drawable.someone_else
)*/
