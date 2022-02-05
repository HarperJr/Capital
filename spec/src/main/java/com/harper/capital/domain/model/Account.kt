package com.harper.capital.domain.model

data class Account(
    val id: Long,
    val name: String,
    val type: AccountType,
    val color: AccountColor,
    val icon: AccountIcon,
    val currency: Currency,
    val balance: Double,
    val isIncluded: Boolean = true,
    val isArchived: Boolean = false,
    val metadata: AccountMetadata? = null
)
