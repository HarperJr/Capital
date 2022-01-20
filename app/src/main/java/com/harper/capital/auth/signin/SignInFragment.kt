package com.harper.capital.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.ui.Scaffold
import com.harper.capital.R
import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CapitalButton
import com.harper.core.component.CapitalTextField
import com.harper.core.component.Chip
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.Separator
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class SignInFragment : ComponentFragment<SignInViewModel>(), EventSender<SignInEvent> {
    override val viewModel: SignInViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            val state by viewModel.state.collectAsState()
            Content(state, this)
        }
    }

    companion object {

        fun newInstance(): SignInFragment = SignInFragment()
    }
}

val Apple
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_apple)
val Google
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_google)

@Composable
private fun Content(state: SignInState, es: EventSender<SignInEvent>) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = CapitalTheme.colors.background,
        topBar = {
            Spacer(
                modifier = Modifier
                    .statusBarsHeight()
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            Spacer(
                modifier = Modifier
                    .navigationBarsHeight()
                    .fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            ) {
                val username = rememberSaveable { mutableStateOf(state.username) }
                CapitalTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username.value,
                    placeholder = stringResource(id = R.string.username_or_email_hint),
                    onValueChange = { es.send(SignInEvent.UsernameChange(it)) })
                HorizontalSpacer(height = 24.dp)

                val password = rememberSaveable { mutableStateOf(state.password) }
                CapitalTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password.value,
                    placeholder = stringResource(id = R.string.password_hint),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { es.send(SignInEvent.PasswordChange(it)) }
                )
                HorizontalSpacer(height = 8.dp)
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(id = R.string.forgot_password),
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.Blue
                )
                HorizontalSpacer(height = 16.dp)
                CapitalButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_in)
                ) {

                }
                HorizontalSpacer(height = 16.dp)
                Box(modifier = Modifier.fillMaxWidth()) {
                    Separator(modifier = Modifier.align(Alignment.Center))
                    Text(
                        modifier = Modifier
                            .background(color = CapitalTheme.colors.background)
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.or).uppercase(),
                        color = CapitalTheme.colors.onBackground
                    )
                }
                HorizontalSpacer(height = 16.dp)
                ServiceSignBlock(es)
                HorizontalSpacer(height = 16.dp)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.dont_have_an_account),
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.Blue
                )
            }
            Chip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { es.send(SignInEvent.GoOfflineClick) }
            ) {
                Text(text = stringResource(id = R.string.go_offline), color = CapitalColors.White)
            }
        }
    }
}

@Composable
private fun ServiceSignBlock(es: EventSender<SignInEvent>) {
    CapitalButton(
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
    HorizontalSpacer(height = 16.dp)
    CapitalButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.continue_with_google),
        textColor = CapitalColors.Black,
        buttonColors = capitalButtonColors(backgroundColor = CapitalColors.White),
        border = BorderStroke(width = 1.dp, color = CapitalColors.GreyLight),
        leadingIcon = { Image(imageVector = Google, contentDescription = null) }
    ) {

    }
}

@Preview(showBackground = true, name = "SignIn light")
@Composable
fun ContentLight() {
    ComposablePreview {
        Content(SignInState(), MockEventSender())
    }
}

@Preview(showBackground = true, name = "SignIn dark")
@Composable
fun ContentDark() {
    ComposablePreview(isDark = true) {
        Content(SignInState(), MockEventSender())
    }
}

