package com.harper.capital.auth.signin

import com.harper.capital.auth.signin.model.SignInEvent
import com.harper.capital.auth.signin.model.SignInState
import com.harper.core.ui.ComponentViewModelV1

class SignInMockViewModel : ComponentViewModelV1<SignInState, SignInEvent>(
    initialState = SignInState()
) {

    override fun onEvent(event: SignInEvent) {
        /**nope**/
    }
}
