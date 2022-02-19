package com.harper.capital.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.core.component.CButton
import com.harper.core.component.CChip
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CSeparator
import com.harper.core.component.CTextField
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors
import com.harper.core.ui.ComponentViewModel

val Apple
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_apple)
val Google
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_google)

@Composable
fun SignInScreen(viewModel: ComponentViewModel<SignInState, SignInEvent>) {
    val state by viewModel.state.collectAsState()
    CScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            ) {
                val username = rememberSaveable { mutableStateOf(state.username) }
                CTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username.value,
                    placeholder = stringResource(id = R.string.username_or_email_hint),
                    onValueChange = { viewModel.onEvent(SignInEvent.UsernameChange(it)) })
                CHorizontalSpacer(height = 24.dp)
                val password = rememberSaveable { mutableStateOf(state.password) }
                CTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password.value,
                    placeholder = stringResource(id = R.string.password_hint),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.onEvent(SignInEvent.PasswordChange(it)) }
                )
                CHorizontalSpacer(height = 8.dp)
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(id = R.string.forgot_password),
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.Blue
                )
                CHorizontalSpacer(height = 16.dp)
                CButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_in)
                ) {

                }
                CHorizontalSpacer(height = 16.dp)
                Box(modifier = Modifier.fillMaxWidth()) {
                    CSeparator(modifier = Modifier.align(Alignment.Center))
                    Text(
                        modifier = Modifier
                            .background(color = CapitalTheme.colors.background)
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.or).uppercase(),
                        color = CapitalTheme.colors.onBackground
                    )
                }
                CHorizontalSpacer(height = 16.dp)
                ServiceSignBlock(viewModel)
                CHorizontalSpacer(height = 16.dp)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.dont_have_an_account),
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.Blue
                )
            }
            CChip(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                onClick = { viewModel.onEvent(SignInEvent.ToShelterClick) }
            ) {
                Text(text = stringResource(id = R.string.to_shelter), color = CapitalColors.White)
            }
            CChip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { viewModel.onEvent(SignInEvent.GoOfflineClick) }
            ) {
                Text(text = stringResource(id = R.string.go_offline), color = CapitalColors.White)
            }
        }
    }
}

@Composable
private fun ServiceSignBlock(viewModel: ComponentViewModel<SignInState, SignInEvent>) {
    CButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.continue_with_apple),
        textColor = CapitalTheme.colors.background,
        buttonColors = capitalButtonColors(backgroundColor = CapitalTheme.colors.onBackground),
        leadingIcon = {
            Image(
                imageVector = Apple,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = CapitalTheme.colors.background)
            )
        }
    ) {

    }
    CHorizontalSpacer(height = 16.dp)
    CButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.continue_with_google),
        textColor = CapitalColors.Black,
        buttonColors = capitalButtonColors(backgroundColor = CapitalColors.White),
        border = BorderStroke(width = 1.dp, color = CapitalColors.GreyLight),
        leadingIcon = { Image(imageVector = Google, contentDescription = null) }
    ) {

    }
}

@Preview(showBackground = true, name = "SignIn Light")
@Composable
fun SignInScreenLight() {
    CPreview {
        SignInScreen(SignInMockViewModel())
    }
}

@Preview(showBackground = true, name = "SignIn Dark")
@Composable
fun SignInScreenDark() {
    CPreview(isDark = true) {
        SignInScreen(SignInMockViewModel())
    }
}
