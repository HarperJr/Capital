package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.window.Dialog
import com.harper.core.R
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal const val DAYS_IN_WEEK = 7

private val YYYYDateFormatter = DateTimeFormatter.ofPattern("yyyy")
private val EDDMMDateFormatter = DateTimeFormatter.ofPattern("E, dd MMM")
private val MMMMYYYYDateFormatter = DateTimeFormatter.ofPattern("LLLL yyyy")

@Composable
fun CDatePickerDialog(
    date: LocalDate?,
    dateConstraints: DateConstraints? = null,
    onDismiss: () -> Unit,
    onDateSelect: (LocalDate) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        CDatePickerDialogContent(
            state = rememberDatePickerDialogState(date.orElse(LocalDate.now()), dateConstraints),
            onPositiveClick = {
                onDateSelect.invoke(it)
                onDismiss.invoke()
            },
            onNegativeClick = {
                onDismiss.invoke()
            }
        )
    }
}

@Composable
private fun CDatePickerDialogContent(
    state: DatePickerDialogState,
    onPositiveClick: (LocalDate) -> Unit,
    onNegativeClick: () -> Unit
) {
    Surface(
        color = CapitalTheme.colors.primaryVariant,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Column {
            CDatePickerHeader(date = state.currentDate, state.pickedDate, state.pickedDate)
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
                        val dateBackgroundColor = if (isDaySelected) CapitalTheme.colors.secondary else CapitalColors.Transparent
                        val textColor = if (state.isSelectable(day)) {
                            if (isDaySelected) CapitalColors.White else CapitalTheme.colors.textPrimary
                        } else {
                            CapitalTheme.colors.textSecondary
                        }
                        Box(
                            modifier = Modifier
                                .clickable(enabled = state.isSelectable(day)) { state.pickedDate = day }
                                .background(color = dateBackgroundColor, shape = CircleShape)
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
                    onPositiveClick.invoke(state.pickedDate)
                }
            }
        }
    }
}

@Composable
internal fun rememberDatePickerDialogState(date: LocalDate, constraints: DateConstraints?): DatePickerDialogState =
    remember { DatePickerDialogState(date, constraints) }

internal class DatePickerDialogState(date: LocalDate, private val constraints: DateConstraints?) {
    var currentDate: LocalDate by mutableStateOf(date)
    var pickedDate: LocalDate by mutableStateOf(this.currentDate)

    val firstDayOfMonth: LocalDate
        get() = currentDate.withDayOfMonth(1)

    val daysRange: IntRange
        get() {
            val lastDayOfMonth = currentDate.plusMonths(1L).withDayOfMonth(1)
            val days = Duration.between(firstDayOfMonth.atStartOfDay(), lastDayOfMonth.atStartOfDay()).toDays().toInt()
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

    fun isSelected(date: LocalDate): Boolean =
        pickedDate == date

    fun isSelectable(date: LocalDate): Boolean = isInBounds(date)

    private fun isInBounds(date: LocalDate): Boolean = constraints?.isInBounds(date).orElse(true)
}

class DateConstraints(private val dateStart: LocalDate, private val dateEnd: LocalDate) {

    fun isInBounds(date: LocalDate): Boolean =
        date.isAfter(dateStart) && date.isBefore(dateEnd.plusDays(1L))
}

@Composable
internal fun CDatePickerHeader(date: LocalDate, dateStart: LocalDate?, dateEnd: LocalDate?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CapitalTheme.colors.secondary)
            .padding(CapitalTheme.dimensions.side)
    ) {
        CompositionLocalProvider(LocalContentColor provides CapitalTheme.colors.onSecondary) {
            Text(
                text = date.format(YYYYDateFormatter),
                style = CapitalTheme.typography.title
            )
            if (dateStart != null && dateEnd != null) {
                if (dateStart.isEqual(dateEnd)) {
                    Text(text = dateStart.format(EDDMMDateFormatter), style = CapitalTheme.typography.subtitle)
                } else {
                    Text(
                        text = "${dateStart.format(EDDMMDateFormatter)} - ${dateEnd.format(EDDMMDateFormatter)}",
                        style = CapitalTheme.typography.subtitle
                    )
                }
            } else {
                Text(text = "-", style = CapitalTheme.typography.subtitle)
            }
        }
    }
}

@Composable
internal fun DatePickerLayout(modifier: Modifier = Modifier, firstDayInWeek: Int, content: @Composable () -> Unit) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measureables, constraints ->
            val viewSize = constraints.maxWidth / DAYS_IN_WEEK
            val placeables = measureables.map {
                it.measure(
                    Constraints.fixed(
                        width = viewSize,
                        height = viewSize
                    )
                )
            }
            val height = viewSize * (DAYS_IN_WEEK - 1)
            layout(width = constraints.maxWidth, height = height) {
                for (i in placeables.indices) {
                    val xOffset = (i + firstDayInWeek) % DAYS_IN_WEEK * viewSize
                    val yOffset = (i + firstDayInWeek) / DAYS_IN_WEEK * viewSize
                    placeables[i].placeRelative(xOffset, yOffset)
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun CDatePickerDialogLight() {
    CPreview {
        CDatePickerDialogContent(
            rememberDatePickerDialogState(date = LocalDate.of(2022, 4, 26), constraints = null),
            onPositiveClick = {},
            onNegativeClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CDatePickerDialogDark() {
    CPreview(isDark = true) {
        CDatePickerDialogContent(
            rememberDatePickerDialogState(date = LocalDate.of(2022, 4, 26), constraints = null),
            onPositiveClick = {},
            onNegativeClick = {})
    }
}
