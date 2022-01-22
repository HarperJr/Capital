package com.harper.capital.auth.signin

import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class SignInMockViewModel : ComponentViewModel<SignInState>(
    defaultState = SignInState()
), EventObserver<SignInEvent> {

    override fun onEvent(event: SignInEvent) {
        /**nope**/
    }
}
