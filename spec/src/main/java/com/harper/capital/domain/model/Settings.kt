package com.harper.capital.domain.model

import java.time.LocalDateTime

class Settings(
    val colorTheme: ColorTheme,
    val currency: Currency,
    val currencyLastUpdate: LocalDateTime
)
