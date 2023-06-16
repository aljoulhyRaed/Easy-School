package com.techhunters.easyschool.core.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ui.composable.BasicButton
import com.techhunters.easyschool.core.ext.basicButton
import com.techhunters.easyschool.core.ui.composable.LogoCard
import com.techhunters.easyschool.R.string as AppText

private const val SPLASH_TIMEOUT = 1500L

//@RootNavGraph(start = true)
@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Text(text = stringResource(AppText.generic_error))

            BasicButton(AppText.try_again, Modifier.basicButton()) { viewModel.onAppStart(openAndPopUp) }
        } else {
            if(isSystemInDarkTheme()){
                val painter = painterResource(id = R.drawable.logo_light)
                LogoCard(modifier = modifier, painter =painter )
            }else{
                val painter = painterResource(id = R.drawable.logo_dark)
                LogoCard(modifier = modifier, painter =painter )
            }
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart(openAndPopUp)
    }
}

