package com.harper.capital.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.category.model.CategoryManageBottomSheet
import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManagePage
import com.harper.capital.category.model.CategoryManageState
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CBottomSheetScaffold
import com.harper.core.component.CButton
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreview
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbar
import com.harper.core.component.TabBar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import timber.log.Timber

@Composable
@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
fun CategoryManageScreen(
    viewModel: ComponentViewModel<CategoryManageState, CategoryManageEvent>
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    CBottomSheetScaffold(
        sheetContent = {
            val bottomSheet = remember(state.bottomSheetState) {
                state.bottomSheetState.bottomSheet
            }
            BottomSheetContent(bottomSheet, viewModel)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = { CategoryManageTopBar(viewModel) },
        sheetState = sheetState,
    ) {
        Timber.d("Recomposition")
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val pagerState = rememberPagerState(initialPage = state.selectedPage)
                TabBar(
                    data = state.tabBarData,
                    pagerState = pagerState,
                    onTabSelect = { viewModel.onEvent(CategoryManageEvent.TabSelect(it)) }
                )
                HorizontalPager(state = pagerState, count = state.pages.size) { pageIndex ->
                    PageBlock(page = state.pages[pageIndex], viewModel)
                }
            }
            CButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CapitalTheme.dimensions.side)
                    .navigationBarsWithImePadding(),
                text = stringResource(id = R.string.create_new_category),
                onClick = { viewModel.onEvent(CategoryManageEvent.Apply) }
            )
        }
    }
}

@Composable
fun PageBlock(page: CategoryManagePage, viewModel: ComponentViewModel<CategoryManageState, CategoryManageEvent>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CHorizontalSpacer(height = 32.dp)
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape)
                    .clickable { viewModel.onEvent(CategoryManageEvent.IconSelectClick) }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = page.icon.getImageVector(),
                    contentDescription = null
                )
            }
            CTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                value = page.name,
                placeholder = stringResource(id = R.string.enter_name_hint),
                onValueChange = {
                    viewModel.onEvent(CategoryManageEvent.NameChange(it))
                },
                textColor = CapitalTheme.colors.onBackground
            )
        }
        CHorizontalSpacer(height = 24.dp)
        CAmountTextField(
            modifier = Modifier.fillMaxWidth(),
            amount = page.amount,
            currencyIso = page.currency.name,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            onValueChange = {
                viewModel.onEvent(CategoryManageEvent.AmountChange(it))
            },
            textColor = CapitalTheme.colors.onBackground
        )
        CHorizontalSpacer(height = 24.dp)
        CPreferenceArrow(
            modifier = Modifier.fillMaxWidth(),
            title = "${page.currency.name} ${page.currency.name.formatCurrencySymbol()}",
            subtitle = page.currency.name.formatCurrencyName()
        ) {
            viewModel.onEvent(CategoryManageEvent.CurrencySelectClick)
        }
    }
}

@Composable
private fun BottomSheetContent(
    bottomSheet: CategoryManageBottomSheet?,
    viewModel: ComponentViewModel<CategoryManageState, CategoryManageEvent>
) {
    when (bottomSheet) {
        is CategoryManageBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { viewModel.onEvent(CategoryManageEvent.CurrencySelect(it)) }
            )
        }
        is CategoryManageBottomSheet.Icons -> {
            IconsBottomSheet(
                data = bottomSheet.data,
                onIconSelect = { viewModel.onEvent(CategoryManageEvent.IconSelect(it)) }
            )
        }
        else -> {
        }
    }
}

@Composable
private fun CategoryManageTopBar(viewModel: ComponentViewModel<CategoryManageState, CategoryManageEvent>) {
    CToolbar(
        content = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.new_category),
                style = CapitalTheme.typography.title
            )
        },
        navigation = {
            CIcon(
                imageVector = CapitalIcons.ArrowLeft,
                onClick = { viewModel.onEvent(CategoryManageEvent.BackClick) }
            )
        }
    )
}

@Preview
@Composable
private fun CategoryManageScreenLight() {
    CPreview {
        CategoryManageScreen(CategoryManageMockViewModel())
    }
}

@Preview
@Composable
private fun CategoryManageScreenDark() {
    CPreview(isDark = true) {
        CategoryManageScreen(CategoryManageMockViewModel())
    }
}
