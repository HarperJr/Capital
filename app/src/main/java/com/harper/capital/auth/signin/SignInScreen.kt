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

@Composable
fun SignInScreen(viewModel: ComponentViewModel<SignInState, SignInEvent>) {
    val state by viewModel.state.collectAsState()
    CScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side)
                    .align(Alignment.Center)
            ) {
                CTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.username,
                    placeholder = stringResource(id = R.string.username_or_email_hint),
                    onValueChange = { viewModel.onEvent(SignInEvent.UsernameChange(it)) })
                CHorizontalSpacer(height = 24.dp)
                CTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    placeholder = stringResource(id = R.string.password_hint),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.onEvent(SignInEvent.PasswordChange(it)) }
                )
                CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(id = R.string.forgot_password),
                    style = CapitalTheme.typography.buttonSmall,
                    color = CapitalColors.Blue
                )
                CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                CButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_in)
                ) {

                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CSeparator()
                    Text(
                        modifier = Modifier
                            .background(color = CapitalTheme.colors.background)
                            .padding(horizontal = CapitalTheme.dimensions.small, vertical = CapitalTheme.dimensions.medium),
                        text = stringResource(id = R.string.or).uppercase(),
                        color = CapitalTheme.colors.onBackground
                    )
                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                ServiceSignBlock(viewModel)
                CHorizontalSpacer(height = CapitalTheme.dimensions.side)
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
                    .padding(CapitalTheme.dimensions.side),
                onClick = { viewModel.onEvent(SignInEvent.ToShelterClick) }
            ) {
                Text(text = stringResource(id = R.string.to_shelter), color = CapitalColors.White)
            }
            CChip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(CapitalTheme.dimensions.side),
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
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_apple),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = CapitalTheme.colors.background)
            )
        }
    ) {
        viewModel.onEvent(SignInEvent.SignInAppleClick)
    }
    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
    CButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.continue_with_google),
        textColor = CapitalColors.Black,
        buttonColors = capitalButtonColors(backgroundColor = CapitalColors.White),
        border = BorderStroke(width = 1.dp, color = CapitalColors.GreyLight).takeIf { CapitalTheme.colors.isLight },
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
                contentDescription = null
            )
        }
    ) {
        viewModel.onEvent(SignInEvent.SignInGoogleClick)
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
