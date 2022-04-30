package com.harper.capital.history.model

import java.time.LocalDate

data class DatePickerDialogState(val dateStart: LocalDate? = null, val dateEnd: LocalDate? = null, val isVisible: Boolean)
