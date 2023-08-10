package com.techhunters.easyschool.features.auth.presentation.profile.composable

import ChatAppBar
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ui.DrawerManager
import com.techhunters.easyschool.core.ui.composable.AnimatingFabContent
import com.techhunters.easyschool.core.ui.composable.baselineHeight
import com.techhunters.easyschool.features.auth.domain.models.User



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeader(
    onClicked: () -> Unit = { },
    scrollState: ScrollState,
    data: User,
    containerHeight: Dp
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    ChatAppBar(
        // Reset the minimum bounds that are passed to the root of a compose tree
        modifier = Modifier.wrapContentSize(),
        onNavIconPressed = { DrawerManager.openDrawer() },
        title = { },
        actions = {
            // More icon
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = onClicked)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = stringResource(id = R.string.more_options)
            )
        }
    )
    AsyncImage(
        model = data.imageUrl,
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            // TODO: Update to use offset to avoid recomposition
            .padding(
                start = 16.dp,
                top = offsetDp,
                end = 16.dp
            )
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        contentDescription = null

    )
}

@Composable
fun ProfileProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        Text(
            text = label,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun NameAndPosition(
    userData: User
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Name(
            userData.name,
            modifier = Modifier.baselineHeight(32.dp)
        )
        Position(
            userData.position,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .baselineHeight(24.dp)
        )
    }
}

@Composable
private fun Name(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun Position(position: String, modifier: Modifier = Modifier) {
    Text(
        text = position,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ProfileFab(
    extended: Boolean,
    userIsMe: Boolean,
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = { }
) {
    key(userIsMe) { // Prevent multiple invocations to execute during composition
        FloatingActionButton(
            onClick = onFabClicked,
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            AnimatingFabContent(
                icon = {
                    Icon(
                        imageVector = if (userIsMe) Icons.Outlined.Create else Icons.Outlined.Chat,
                        contentDescription = stringResource(
                            if (userIsMe) R.string.edit_profile else R.string.message
                        )
                    )
                },
                text = {
                    Text(
                        text = stringResource(
                            id = if (userIsMe) R.string.edit_profile else R.string.message
                        ),
                    )
                },
                extended = extended
            )
        }
    }
}

@Composable
fun ProfileError() {
    Text(stringResource(R.string.profile_error))
}