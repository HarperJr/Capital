package com.harper.capital.auth.signin

import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel

class SignInViewModel(private val router: GlobalRouter) : ComponentViewModel<SignInState, SignInEvent>(initialState = SignInState()) {

    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UsernameChange -> onUserNameChange(event)
            is SignInEvent.PasswordChange -> onPasswordChange(event)
            is SignInEvent.GoOfflineClick -> router.setMainAsRoot()
            is SignInEvent.ToShelterClick -> router.shelter()
        }
    }

    private fun onUserNameChange(event: SignInEvent.UsernameChange) {
        update { it.copy(username = event.username) }
    }

    private fun onPasswordChange(event: SignInEvent.PasswordChange) {
        update { it.copy(password = event.password) }
    }
}
