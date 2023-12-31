package com.techhunters.easyschool.features.chat.data

import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.features.chat.presentation.conversation.ConversationUiState

 /*val initialMessages = listOf(
    MessageEntity(
        "me",
        "Check it out!",
        "8:07 PM"
    ),
    MessageEntity(
        "me",
        "Thank you!$EMOJI_PINK_HEART",
        "8:06 PM",
        R.drawable.sticker
    ),
    MessageEntity(
        "Taylor Brooks",
        "You can use all the same stuff",
        "8:05 PM"
    ),
    MessageEntity(
        "Taylor Brooks",
        "@aliconors Take a look at the `Flow.collectAsStateWithLifecycle()` APIs",
        "8:05 PM"
    ),
    MessageEntity(
        "John Glenn",
        "Compose newbie as well $EMOJI_FLAMINGO, have you looked at the JetNews sample? " +
                "Most blog posts end up out of date pretty fast but this sample is always up to " +
                "date and deals with async data loading (it's faked but the same idea " +
                "applies) $EMOJI_POINTS https://goo.gle/jetnews",
        "8:04 PM"
    ),
    MessageEntity(
        "me",
        "Compose newbie: I’ve scourged the internet for tutorials about async data " +
                "loading but haven’t found any good ones $EMOJI_MELTING $EMOJI_CLOUDS. " +
                "What’s the recommended way to load async data and emit composable widgets?",
        "8:03 PM"
    )
)*/

val exampleUiState = ConversationUiState(
  //  initialMessages = initialMessages,
    channelName = "#composers",
    channelMembers = 42
)

/**
 * Example colleague profile
 */
val colleagueProfile = Student(
    id = "12345",
    imageUrl = "R.drawable.someone_else",
    name = "Taylor Brooks",
    status = "Away",
    position = "Senior Android Dev at Openlane",
   // twitter = "twitter.com/taylorbrookscodes",
    classId = "sagjki",
    guardianPhoneNum = "789797",
    role = "student"
)

/**
 * Example "me" profile.
 */
val meProfile = Teacher(
    id = "me",
    imageUrl = "R.drawable.ali",
    name = "Ali Conors",
    status = "Online",
    position = "Senior Android Dev at Yearin\nGoogle Developer Expert",
   // twitter = "twitter.com/aliconors",
role = "teacher",
phoneNum = "8889898779789"

)

object EMOJIS {
    // EMOJI 15
    const val EMOJI_PINK_HEART = "\uD83E\uDE77"

    // EMOJI 14 🫠
    const val EMOJI_MELTING = "\uD83E\uDEE0"

    // ANDROID 13.1 😶‍🌫️
    const val EMOJI_CLOUDS = "\uD83D\uDE36\u200D\uD83C\uDF2B️"

    // ANDROID 12.0 🦩
    const val EMOJI_FLAMINGO = "\uD83E\uDDA9"

    // ANDROID 12.0  👉
    const val EMOJI_POINTS = " \uD83D\uDC49"
}
