package com.techhunters.easyschool.features.auth.presentation.email_login


import BasicToolbar
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.R
import com.techhunters.easyschool.R.string as AppText
import com.techhunters.easyschool.core.ui.composable.*
import com.techhunters.easyschool.core.ext.basicButton
import com.techhunters.easyschool.core.ext.fieldModifier
import com.techhunters.easyschool.core.ext.textButton

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    role:String,
    viewModel: LoginViewModel = hiltViewModel()
) {

    viewModel.initialize(role)

    val uiState by viewModel.uiState

    BasicToolbar(AppText.login_details)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*if(isSystemInDarkTheme()){
            val painter = painterResource(id = R.drawable.logo_light)
            LogoCard(modifier = modifier, painter =painter )
        }else{
            val painter = painterResource(id = R.drawable.logo_dark)
            LogoCard(modifier = modifier, painter =painter )
        }*/
        Spacer(modifier = modifier.height(10.dp))
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.sign_in, Modifier.basicButton()) { viewModel.onSignInClick(openAndPopUp) }

        BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick()
        }
    }
}
