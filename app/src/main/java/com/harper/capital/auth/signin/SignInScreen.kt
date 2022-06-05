package com.harper.capital.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.core.component.CButtonCommon
import com.harper.core.component.CButtonPrimary
import com.harper.core.component.CChip
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CSeparator
import com.harper.core.component.CTextField
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel

@Composable
fun SignInScreen(viewModel: ComponentViewModel<SignInState, SignInEvent>) {
    val state by viewModel.state.collectAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }
    CScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.large)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.large)
            ) {
                CTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.username,
                    placeholder = stringResource(id = R.string.username_or_email_hint),
                    leadingIcon = { Icon(CapitalIcons.Account, null) },
                    onValueChange = { viewModel.onEvent(SignInEvent.UsernameChange(it)) }
                )
                Column {
                    CTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        placeholder = stringResource(id = R.string.password_hint),
                        leadingIcon = { Icon(CapitalIcons.Password, null) },
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Icon(
                                    if (isPasswordVisible) CapitalIcons.EyeOff else CapitalIcons.EyeOn, null
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        onValueChange = { viewModel.onEvent(SignInEvent.PasswordChange(it)) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.small)
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = stringResource(id = R.string.forgot_password),
                        style = CapitalTheme.typography.buttonSmall,
                        color = CapitalColors.Blue
                    )
                }
                CButtonPrimary(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_in).uppercase()
                ) {

                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    CSeparator(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier
                            .background(color = CapitalColors.Transparent)
                            .padding(horizontal = CapitalTheme.dimensions.small, vertical = CapitalTheme.dimensions.medium),
                        text = stringResource(id = R.string.or).uppercase(),
                        color = CapitalTheme.colors.onBackground,
                        textAlign = TextAlign.Center
                    )
                    CSeparator(modifier = Modifier.weight(1f))
                }
                CButtonCommon(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.continue_with_google),
                    elevation = 4.dp,
                    icon = {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
                            contentDescription = null
                        )
                    }
                ) {
                    viewModel.onEvent(SignInEvent.SignInGoogleClick)
                }
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
