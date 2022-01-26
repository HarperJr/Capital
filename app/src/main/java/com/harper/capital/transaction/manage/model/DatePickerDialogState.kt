package com.harper.capital.transaction.manage.model

import java.time.LocalDate

data class DatePickerDialogState(val date: LocalDate = LocalDate.now(), val isVisible: Boolean)
