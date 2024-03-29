package com.harper.capital.domain.model

import java.time.LocalDateTime

class Settings(
    val colorTheme: ColorTheme,
    val accountPresentation: AccountPresentation,
    val currency: Currency,
    val currencyLastUpdate: LocalDateTime
)
