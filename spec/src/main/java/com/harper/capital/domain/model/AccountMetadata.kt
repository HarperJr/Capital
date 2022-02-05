package com.harper.capital.domain.model

sealed class AccountMetadata {

    class LoanAsset(val limit: Double) : AccountMetadata()

    class GoalAsset(val goal: Double) : AccountMetadata()

    class Investment(val percent: Double) : AccountMetadata()
}
