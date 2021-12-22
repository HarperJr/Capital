package com.harper.capital.expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.expense.model.ExpenseAddEvent
import com.harper.capital.expense.model.ExpenseCategoryAddBottomSheet
import com.harper.capital.expense.model.ExpenseCategoryAddState
import com.harper.capital.expense.model.ExpenseCategoryAddStateProvider
import com.harper.capital.ext.getImageVector
import com.harper.core.component.AmountTextField
import com.harper.core.component.CapitalTextField
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.Toolbar
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class ExpenseCategoryAddFragment : ComponentFragment<ExpenseCategoryAddViewModel>(), EventSender<ExpenseAddEvent> {
    override val viewModel: ExpenseCategoryAddViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        Content(state, this)
    }

    companion object {

        fun newInstance(): ExpenseCategoryAddFragment = ExpenseCategoryAddFragment()
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun Content(state: ExpenseCategoryAddState, es: EventSender<ExpenseAddEvent>) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheet = remember(state.bottomSheetState.bottomSheet) {
        state.bottomSheetState.bottomSheet
    }

    BottomSheetScaffold(
        topBar = { ExpenseCategoryAddTopBar() },
        sheetContent = {
            BottomSheetContent(bottomSheet, es)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    scaffoldState.bottomSheetState.expand()
                } else {
                    scaffoldState.bottomSheetState.collapse()
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetBackgroundColor = CapitalTheme.colors.background,
        sheetElevation = 6.dp,
        sheetPeekHeight = 0.dp,
        sheetShape = CapitalTheme.shapes.bottomSheet
    ) {
        val nameValue = remember { mutableStateOf(state.name) }
        val amountValue = remember { mutableStateOf(state.amount) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = CapitalTheme.colors.background)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = state.icon.getImageVector(),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
                    )
                }
                CapitalTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    value = nameValue.value,
                    placeholder = stringResource(id = R.string.enter_name_hint),
                    onValueChange = { nameValue.value = it },
                    textColor = CapitalTheme.colors.onBackground
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                AmountTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    amount = amountValue.value,
                    placeholder = stringResource(id = R.string.enter_amount_hint),
                    onValueChange = { amountValue.value = it },
                    textColor = CapitalTheme.colors.onBackground
                )
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                        .clickable { es.send(ExpenseAddEvent.CurrencySelectClick) }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = state.currency.name.formatCurrencySymbol(),
                        color = CapitalTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomSheetContent(bottomSheet: ExpenseCategoryAddBottomSheet?, es: EventSender<ExpenseAddEvent>) {
    when (bottomSheet) {
        is ExpenseCategoryAddBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { es.send(ExpenseAddEvent.CurrencySelect(it)) }
            )
        }
        is ExpenseCategoryAddBottomSheet.Icons -> {
            IconsBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                title = stringResource(id = R.string.select_icon),
                data = bottomSheet.data,
                onIconSelect = {}
            )
        }
        else -> {}
    }
}

@Composable
private fun ExpenseCategoryAddTopBar() {
    Toolbar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.add_expense_category),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(imageVector = CapitalIcons.Navigation)
        }
    )
}

@Preview
@Composable
private fun ContentLight(@PreviewParameter(ExpenseCategoryAddStateProvider::class) state: ExpenseCategoryAddState) {
    ComposablePreview {
        Content(state = state, MockEventSender())
    }
}

@Preview
@Composable
private fun ContentDark(@PreviewParameter(ExpenseCategoryAddStateProvider::class) state: ExpenseCategoryAddState) {
    ComposablePreview(isDark = true) {
        Content(state = state, MockEventSender())
    }
}
