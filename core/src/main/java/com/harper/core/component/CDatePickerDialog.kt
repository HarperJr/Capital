package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.harper.core.R
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DAYS_IN_WEEK = 7

private val YYYYDateFormatter = DateTimeFormatter.ofPattern("yyyy")
private val EDDMMDateFormatter = DateTimeFormatter.ofPattern("E, dd MMM")
private val MMMMYYYYDateFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")

@Composable
fun CDatePickerDialog(date: LocalDate, onDismiss: () -> Unit, onDateSelect: (LocalDate) -> Unit) {
    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        CDatePickerDialogContent(
            date = date,
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
    date: LocalDate,
    onPositiveClick: (LocalDate) -> Unit,
    onNegativeClick: () -> Unit
) {
    Surface(
        color = CapitalTheme.colors.primaryVariant,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        val state = rememberDatePickerDiaogState(date)
        Column {
            CDatePickerHeader(state)
            CHorizontalSpacer(height = CapitalTheme.dimensions.small)
            Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CIcon(imageVector = CapitalIcons.ArrowLeft) {
                        state.toPreviousMonth()
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.date.format(MMMMYYYYDateFormatter),
                        style = CapitalTheme.typography.button,
                        textAlign = TextAlign.Center
                    )
                    CIcon(imageVector = CapitalIcons.ArrowRight) {
                        state.toNextMonth()
                    }
                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.small)

                Layout(
                    modifier = Modifier.background(color = CapitalTheme.colors.primaryVariant),
                    content = {
                        (0 until state.daysRange).forEach {
                            val dayDate = state.dateStart.plusDays(it.toLong())
                            val dateBackgroundColor =
                                if (state.isSelected(dayDate)) CapitalTheme.colors.secondary else CapitalColors.Transparent
                            Box(
                                modifier = Modifier
                                    .clickable { state.selectedDate = dayDate }
                                    .background(color = dateBackgroundColor, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayDate.dayOfMonth.toString(),
                                    color = CapitalTheme.colors.textPrimary
                                )
                            }
                        }
                    },
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
                                val xOffset =
                                    (i + state.firstDayInWeekOffset) % DAYS_IN_WEEK * viewSize
                                val yOffset =
                                    (i + state.firstDayInWeekOffset) / DAYS_IN_WEEK * viewSize
                                placeables[i].placeRelative(xOffset, yOffset)
                            }
                        }
                    })
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(CapitalTheme.dimensions.side)
            ) {
                CButton(text = stringResource(id = R.string.cancel), borderless = true) {
                    onNegativeClick.invoke()
                }
                CVerticalSpacer(width = CapitalTheme.dimensions.side)
                CButton(text = stringResource(id = R.string.ok), borderless = true) {
                    onPositiveClick.invoke(state.selectedDate)
                }
            }
        }
    }
}

@Composable
fun rememberDatePickerDiaogState(date: LocalDate): DatePickerDialogState =
    remember { DatePickerDialogState(date) }

class DatePickerDialogState(initialDate: LocalDate) {
    var date: LocalDate by mutableStateOf(initialDate)
    var selectedDate: LocalDate by mutableStateOf(date)

    val dateStart: LocalDate
        get() = date.withDayOfMonth(1)

    val daysRange: Int
        get() {
            val endDate = date.plusMonths(1L).withDayOfMonth(1)
            return Duration.between(dateStart.atStartOfDay(), endDate.atStartOfDay()).toDays()
                .toInt()
        }

    val firstDayInWeekOffset: Int
        get() = dateStart.dayOfWeek.value % DAYS_IN_WEEK

    fun toPreviousMonth() {
        date = date.minusMonths(1L)
    }

    fun toNextMonth() {
        date = date.plusMonths(1L)
    }

    fun isSelected(date: LocalDate): Boolean =
        selectedDate == date
}

@Composable
private fun CDatePickerHeader(state: DatePickerDialogState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CapitalTheme.colors.secondary)
            .padding(CapitalTheme.dimensions.side)
    ) {
        CompositionLocalProvider(LocalContentColor provides CapitalTheme.colors.onSecondary) {
            Text(
                text = state.date.format(YYYYDateFormatter),
                style = CapitalTheme.typography.title
            )
            Text(text = state.selectedDate.format(EDDMMDateFormatter), fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CDatePickerDialogLight() {
    CPreview {
        CDatePickerDialogContent(date = LocalDate.now(), {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun CDatePickerDialogDark() {
    CPreview(isDark = true) {
        CDatePickerDialogContent(date = LocalDate.now(), {}, {})
    }
}
