package com.techhunters.easyschool.features.chat.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import kotlinx.coroutines.launch

@Composable
fun Messages(
    authorMe: String,
    messages: State<List<ChatMessage>>,
    navigateToProfile: (String) -> Unit,
    getAuthorImageUrl: () -> String,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {

        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize()
        ) {
            for (index in messages.value.indices) {
                val prevAuthor = messages.value.getOrNull(index - 1)?.authorId
                val nextAuthor = messages.value.getOrNull(index + 1)?.authorId
                val content = messages.value[index]

                val isFirstMessageByAuthor = prevAuthor != content.authorId
                val isLastMessageByAuthor = nextAuthor != content.authorId


                val messageDate = messages.value.last().timestamp.toDate().day
                val compareDate = Timestamp.now().toDate().day
                if (messageDate != compareDate) {
                    item {
                        val date = messages.value.last().timestamp.toDate().toString()
                        DayHeader(date)
                    }
                } else {
                    item {
                        DayHeader("اليوم")
                    }
                }

                item {
                    Message(
                        onAuthorClick = { authorId -> navigateToProfile(authorId) },
                        msg = content,
                        isUserMe = content.authorId == authorMe,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor,
                        getAuthorImageUrl = { getAuthorImageUrl() }
                    )
                }
            }
        }
        // Jump to bottom button shows up when user scrolls past a threshold.
        // Convert to pixels:
        val jumpThreshold = with(LocalDensity.current) {
            JumpToBottomThreshold.toPx()
        }

        // Show the button if the first visible item is not the first one or if the offset is
        // greater than the threshold.
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            // Only show if the scroller is not at the bottom
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

private val JumpToBottomThreshold = 56.dp
const val ConversationTestTag = "ConversationTestTag"
