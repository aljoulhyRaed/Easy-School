package com.techhunters.easyschool.features.chat.presentation.conversation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techhunters.easyschool.R
import com.techhunters.easyschool.features.chat.domain.models.ChatMessage
import com.techhunters.easyschool.features.chat.presentation.composable.ClassAndSubjectNamesBar
import com.techhunters.easyschool.features.chat.presentation.composable.Messages
import com.techhunters.easyschool.features.chat.presentation.composable.UserInput
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    subId: String,
    gradeId: String,
    segmentName: String,
    viewModel: ConversationViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit,
    onNavIconPressed: () -> Unit = { }
) {
    val authorMe = viewModel.getCurrentUserId()

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    val messages = viewModel.getMessages(gradeId, subId).collectAsStateWithLifecycle(
        emptyList()
    )

    Scaffold(
        topBar = {
            ClassAndSubjectNamesBar(
                channelName = "uiState.channelName",
                channelMembers = 9,
                scrollBehavior = scrollBehavior,
            )
        },
        // Exclude ime and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Messages(
                messages = messages,
                navigateToProfile = { userId ->
                    viewModel.navigateToProfile(open = navigateToProfile, userId = userId)
                },
                modifier = Modifier.weight(1f),
                scrollState = scrollState,
                authorMe = authorMe,
                getAuthorImageUrl = {viewModel.getAuthorImageUrl()}
            )
            UserInput(
                onMessageSent = { content ->
                    viewModel.addMessage(
                        msg = ChatMessage(
                            authorId = authorMe,
                            content = content,
                        ),
                        gradeId = gradeId,
                        subId = subId,
                        segmentName = segmentName
                    )
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // let this element handle the padding so that the elevation is shown behind the
                // navigation bar
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}
