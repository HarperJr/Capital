package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.harper.core.R
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val MMMMYYYYDateFormatter = DateTimeFormatter.ofPattern("LLLL yyyy")
private val dateStartShape = CircleShape.copy(topEnd = CornerSize(0.dp), bottomEnd = CornerSize(0.dp))
private val dateEndShape = CircleShape.copy(topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp))

@Composable
fun CDateRangeDialog(
    dateStart: LocalDate?,
    dateEnd: LocalDate?,
    dateConstraints: DateConstraints? = null,
    onDismiss: () -> Unit,
    onDatesSelect: (LocalDate?, LocalDate?) -> Unit
) {
    val dates = remember(dateStart, dateEnd) {
        if (dateStart == dateEnd) Pair(null, null) else dateStart to dateEnd
    }
    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        CDateRangeDialogContent(
            state = rememberDateRangeDialogState(
                date = dateEnd.orElse(LocalDate.now()),
                dateStart = dates.first,
                dateEnd = dates.second,
                dateConstraints
            ),
            onPositiveClick = { dateStart, dateEnd ->
                onDatesSelect.invoke(dateStart, dateEnd)
                onDismiss.invoke()
            },
            onNegativeClick = {
                onDismiss.invoke()
            }
        )
    }
}

@Composable
private fun CDateRangeDialogContent(
    state: DateRangeDialogState,
    onPositiveClick: (LocalDate?, LocalDate?) -> Unit,
    onNegativeClick: () -> Unit
) {
    val (dateStart, dateEnd) = state.pickedDates
    Surface(
        color = CapitalTheme.colors.primaryVariant,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Column {
            CDatePickerHeader(date = state.currentDate, dateStart, dateEnd)
            CHorizontalSpacer(height = CapitalTheme.dimensions.small)
            Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CIcon(imageVector = CapitalIcons.ArrowLeft, isEnabled = state.isPreviousMonthAvailable()) {
                        state.toPreviousMonth()
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.currentDate.format(MMMMYYYYDateFormatter),
                        style = CapitalTheme.typography.button,
                        textAlign = TextAlign.Center
                    )
                    CIcon(imageVector = CapitalIcons.ArrowRight, isEnabled = state.isNextMonthAvailable()) {
                        state.toNextMonth()
                    }
                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.small)

                DatePickerLayout(
                    modifier = Modifier.background(color = CapitalTheme.colors.primaryVariant),
                    firstDayInWeek = state.firstDayInWeekOffset
                ) {
                    state.daysRange.forEach {
                        val day = state.firstDayOfMonth.plusDays(it.toLong())
                        val isDaySelected = state.isSelected(day)
                        val dayBackgroundColor = if (isDaySelected) CapitalTheme.colors.secondary else CapitalColors.Transparent
                        val textColor = if (state.isSelectable(day)) {
                            if (isDaySelected) CapitalColors.White else CapitalTheme.colors.textPrimary
                        } else {
                            CapitalTheme.colors.textSecondary
                        }
                        val dayShape = when (day) {
                            dateStart -> if (dateEnd == null) CircleShape else dateStartShape
                            dateEnd -> if (dateStart == null) CircleShape else dateEndShape
                            else -> RectangleShape
                        }
                        Box(
                            modifier = Modifier
                                .clickable(enabled = state.isSelectable(day)) { state.pickDate(day) }
                                .background(color = dayBackgroundColor, shape = dayShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.dayOfMonth.toString(),
                                color = textColor
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(CapitalTheme.dimensions.side)
                ) {
                    CButtonPrimary(text = stringResource(id = R.string.cancel)) {
                        onNegativeClick.invoke()
                    }
                    CVerticalSpacer(width = CapitalTheme.dimensions.side)
                    CButtonPrimary(text = stringResource(id = R.string.ok).uppercase()) {
                        onPositiveClick.invoke(dateStart, dateEnd)
                    }
                }
            }
        }
    }
}

@Composable
internal fun rememberDateRangeDialogState(
    date: LocalDate,
    dateStart: LocalDate?,
    dateEnd: LocalDate?,
    constraints: DateConstraints?
): DateRangeDialogState = remember { DateRangeDialogState(date, dateStart, dateEnd, constraints) }

internal class DateRangeDialogState(
    date: LocalDate,
    dateStart: LocalDate?,
    dateEnd: LocalDate?,
    private val constraints: DateConstraints?
) {
    var currentDate: LocalDate by mutableStateOf(date)
    var pickedDates: Pair<LocalDate?, LocalDate?> by mutableStateOf(dateStart to dateEnd)

    val firstDayOfMonth: LocalDate
        get() {
            val firstDayOfMonth = currentDate.withDayOfMonth(1)
            return firstDayOfMonth.minusDays(firstDayOfMonth.dayOfWeek.value.toLong())
        }

    val daysRange: IntRange
        get() {
            val lastDayOfMonth = currentDate.plusMonths(1L).withDayOfMonth(1)
            val lastDay = lastDayOfMonth.plusDays(lastDayOfMonth.dayOfWeek.value - 1L)
            val days = Duration.between(firstDayOfMonth.atStartOfDay(), lastDay.atStartOfDay()).toDays().toInt()
            return (0..days)
        }

    val firstDayInWeekOffset: Int
        get() = firstDayOfMonth.dayOfWeek.value % DAYS_IN_WEEK

    fun toPreviousMonth() {
        currentDate = currentDate.minusMonths(1L)
    }

    fun toNextMonth() {
        currentDate = currentDate.plusMonths(1L)
    }

    fun isPreviousMonthAvailable(): Boolean = isInBounds(currentDate.minusMonths(1L))

    fun isNextMonthAvailable(): Boolean = isInBounds(currentDate.plusMonths(1L))

    fun isSelected(date: LocalDate): Boolean {
        val (dateStart, dateEnd) = pickedDates
        return if (dateStart != null && dateEnd != null) {
            date.isAfter(dateStart.minusDays(1L)) && date.isBefore(dateEnd.plusDays(1L))
        } else {
            date == dateStart || date == dateEnd
        }
    }

    fun isSelectable(date: LocalDate): Boolean = isInBounds(date)

    fun pickDate(date: LocalDate) {
        val (dateStart, dateEnd) = pickedDates
        val newPickedDates = when {
            dateStart == null -> pickedDates.copy(first = date)
            date == dateStart -> pickedDates.copy(second = null)
            dateEnd == null -> {
                if (date.isBefore(dateStart)) {
                    pickedDates.copy(first = date, second = dateStart)
                } else {
                    pickedDates.copy(second = date)
                }
            }
            date == dateEnd -> pickedDates.copy(first = null)
            else -> pickedDates.copy(first = date, second = null)
        }
        pickedDates = newPickedDates
    }

    private fun isInBounds(date: LocalDate): Boolean = constraints?.isInBounds(date).orElse(true)
}

@Preview(showBackground = true)
@Composable
fun CDateRangeDialogLight() {
    CPreview {
        CDateRangeDialogContent(
            rememberDateRangeDialogState(
                date = LocalDate.of(2022, 4, 26),
                dateStart = LocalDate.of(2022, 4, 10),
                dateEnd = LocalDate.of(2022, 4, 20),
                constraints = null
            ),
            onPositiveClick = { _, _ -> },
            onNegativeClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CDateRangeDialogDark() {
    CPreview(isDark = true) {
        CDateRangeDialogContent(
            rememberDateRangeDialogState(
                date = LocalDate.of(2022, 4, 26),
                dateStart = LocalDate.of(2022, 4, 10),
                dateEnd = LocalDate.of(2022, 4, 20),
                constraints = null
            ),
            onPositiveClick = { _, _ -> },
            onNegativeClick = {})
    }
}
