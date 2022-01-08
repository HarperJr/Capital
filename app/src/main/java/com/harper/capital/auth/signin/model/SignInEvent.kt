package com.harper.capital.auth.signin.model

sealed class SignInEvent {

    class UsernameChange(val username: String) : SignInEvent()

    class PasswordChange(val password: String) : SignInEvent()

    object GoOfflineClick : SignInEvent()
}
