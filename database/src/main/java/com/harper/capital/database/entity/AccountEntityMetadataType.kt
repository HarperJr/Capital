package com.harper.capital.database.entity

/**
 * Associates account with metadata to extend subtypes having their own data
 */
enum class AccountEntityMetadataType {
    UNDEFINED,
    LOAN,
    GOAL,
    INVESTMENT
}
