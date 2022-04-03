package com.harper.capital.history.model

import java.time.LocalDate

data class DatePickerDialogState(val date: LocalDate = LocalDate.now(), val isVisible: Boolean)
