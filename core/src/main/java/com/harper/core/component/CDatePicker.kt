package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.harper.core.ext.compose.fullyVisibleItemIndex
import com.harper.core.ext.compose.heightOrZero
import com.harper.core.ext.compose.widthOrZero
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private const val selectorRectId = "SelectorRect"
private const val dateListId = "DateList"
private val CalendarDateFormatter = DateTimeFormatter.ofPattern("MMM, E")

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun CDatePicker(
    modifier: Modifier = Modifier,
    dateStart: LocalDate,
    dateEnd: LocalDate,
    date: LocalDate,
    onDateSelectClick: (LocalDate) -> Unit,
    onDateSelect: (LocalDate) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val state = rememberDatePickerState(date, dateStart, dateEnd)
    val dateListState = rememberLazyListState(initialFirstVisibleItemIndex = state.dateIndex)
    val flingBehavior = rememberSnapperFlingBehavior(
        lazyListState = dateListState,
        snapOffsetForItem = SnapOffsets.End
    )
    LaunchedEffect(dateListState.isScrollInProgress) {
        snapshotFlow {
            dateListState.layoutInfo.fullyVisibleItemIndex {
                viewportEndOffset - ((viewportEndOffset - viewportStartOffset) / 2f) * 0.25f
            }
        }.collect { dateIndex ->
            state.selectedDate = dateStart.plusDays(dateIndex.toLong())
            onDateSelect.invoke(state.selectedDate)
        }
    }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        CIcon(imageVector = CapitalIcons.Calendar, onClick = {
            onDateSelectClick.invoke(state.selectedDate)
        })
        Box(
            modifier = Modifier
                .size(width = 2.dp, height = CapitalTheme.dimensions.large)
                .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape)
        )
        Layout(
            modifier = Modifier.weight(1f),
            content = {
                Box(
                    modifier = Modifier
                        .layoutId(selectorRectId)
                        .padding(CapitalTheme.dimensions.small)
                        .background(
                            color = CapitalTheme.colors.primaryVariant,
                            shape = CapitalTheme.shapes.medium
                        )
                )
                LazyRow(
                    modifier = Modifier
                        .layoutId(dateListId)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = CapitalTheme.dimensions.medium),
                    state = dateListState,
                    flingBehavior = flingBehavior
                ) {
                    items(state.daysRange) { dayIndex ->
                        DateItem(
                            modifier = Modifier.fillParentMaxWidth(0.25f),
                            dateStart = dateStart,
                            dayIndex = dayIndex,
                            onClick = {
                                coroutineScope.launch {
                                    dateListState.animateScrollToItem(it - 3)
                                }
                            }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                val dateListPlaceable =
                    measurables.find { it.layoutId == dateListId }?.measure(constraints)
                val selectorRectPlaceable =
                    measurables.find { it.layoutId == selectorRectId }?.measure(
                        Constraints.fixed(
                            (constraints.maxWidth * 0.25f).roundToInt(),
                            dateListPlaceable.heightOrZero()
                        )
                    )

                val width = dateListPlaceable.widthOrZero()
                val height = dateListPlaceable.heightOrZero()
                layout(width, height) {
                    selectorRectPlaceable?.placeRelative(width - selectorRectPlaceable.width, 0)
                    dateListPlaceable?.placeRelative(0, 0)
                }
            }
        )
    }
}

@Composable
fun rememberDatePickerState(
    date: LocalDate,
    dateStart: LocalDate,
    dateEnd: LocalDate
): DatePickerState = remember {
    DatePickerState(date, dateStart, dateEnd)
}

class DatePickerState(
    initialDate: LocalDate,
    private val dateStart: LocalDate,
    private val dateEnd: LocalDate
) {
    var date: LocalDate by mutableStateOf(initialDate)
    var selectedDate: LocalDate by mutableStateOf(date)

    val dateIndex: Int
        get() = (Duration.between(dateStart.atStartOfDay(), selectedDate.atStartOfDay())
            .toDays()).toInt()

    val daysRange: Int
        get() = (Duration.between(dateStart.atStartOfDay(), dateEnd.atStartOfDay())
            .toDays()).toInt() + 1
}

@Composable
private fun DateItem(
    modifier: Modifier = Modifier,
    dateStart: LocalDate,
    dayIndex: Int,
    onClick: (Int) -> Unit
) {
    val date = remember { dateStart.plusDays(dayIndex.toLong()) }
    Column(
        modifier = modifier
            .padding(horizontal = CapitalTheme.dimensions.small)
            .clickable { onClick.invoke(dayIndex) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = CapitalTheme.typography.subtitle,
            color = CapitalTheme.colors.textPrimary
        )
        Text(text = date.format(CalendarDateFormatter), color = CapitalTheme.colors.textSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun CDatePickerLight() {
    CPreview {
        CDatePicker(
            dateStart = LocalDate.now().minusDays(30),
            dateEnd = LocalDate.now(),
            date = LocalDate.now(),
            onDateSelectClick = {},
            onDateSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CDatePickerDark() {
    CPreview(isDark = true) {
        CDatePicker(
            dateStart = LocalDate.now().minusDays(30),
            dateEnd = LocalDate.now(),
            date = LocalDate.now(),
            onDateSelectClick = {},
            onDateSelect = {}
        )
    }
}
