package com.harper.capital.auth.signin

import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.core.ui.ComponentViewModel

class SignInMockViewModel : ComponentViewModel<SignInState, SignInEvent>(
    initialState = SignInState()
) {

    override fun onEvent(event: SignInEvent) {
        /**nope**/
    }
}
