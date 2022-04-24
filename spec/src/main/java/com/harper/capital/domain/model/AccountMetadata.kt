package com.harper.capital.domain.model

sealed class AccountMetadata {

    data class Loan(val limit: Double) : AccountMetadata()

    data class Goal(val goal: Double) : AccountMetadata()

    data class Investment(val percent: Double) : AccountMetadata()
}
