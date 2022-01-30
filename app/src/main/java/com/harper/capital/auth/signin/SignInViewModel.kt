package com.harper.capital.auth.signin

import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class SignInViewModel(private val router: GlobalRouter) : ComponentViewModel<SignInState>(
    defaultState = SignInState()
), EventObserver<SignInEvent> {

    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UsernameChange -> onUserNameChange(event)
            is SignInEvent.PasswordChange -> onPasswordChange(event)
            is SignInEvent.GoOfflineClick -> router.setMainAsRoot()
            SignInEvent.ToShelterClick -> router.shelter()
        }
    }

    private fun onUserNameChange(event: SignInEvent.UsernameChange) {
        mutateState { it.copy(username = event.username) }
    }

    private fun onPasswordChange(event: SignInEvent.PasswordChange) {
        mutateState { it.copy(password = event.password) }
    }
}
