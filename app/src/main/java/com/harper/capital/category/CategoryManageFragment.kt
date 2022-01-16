package com.harper.capital.category

import android.os.Parcelable
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.category.model.CategoryManageBottomSheet
import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManageState
import com.harper.capital.category.model.CategoryManageStateProvider
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.ext.getImageVector
import com.harper.core.component.AmountTextField
import com.harper.core.component.ArrowSettingBox
import com.harper.core.component.CapitalButton
import com.harper.core.component.CapitalTextField
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.MenuIcon
import com.harper.core.component.TabBar
import com.harper.core.component.Toolbar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.coroutines.flow.collect
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

class CategoryManageFragment : ComponentFragment<CategoryManageViewModel>(), EventSender<CategoryManageEvent> {
    override val viewModel: CategoryManageViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        Content(state, this)
    }

    @Parcelize
    data class Params(val type: CategoryManageType) : Parcelable

    companion object {
        private const val PARAMS = "category_manage_params"

        fun newInstance(params: Params): CategoryManageFragment =
            CategoryManageFragment().withArgs(PARAMS to params)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
private fun Content(state: CategoryManageState, es: EventSender<CategoryManageEvent>) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheet = remember(state.bottomSheetState.bottomSheet) {
        state.bottomSheetState.bottomSheet
    }

    BottomSheetScaffold(
        topBar = { ExpenseCategoryAddTopBar(es) },
        backgroundColor = CapitalTheme.colors.background,
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
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                val pagerState = rememberPagerState(initialPage = state.selectedPage)
                TabBar(
                    data = state.tabBarData,
                    selectedTabIndex = pagerState.currentPage,
                    onTabSelect = {}
                )
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect {
                        es.send(CategoryManageEvent.TabSelect(it))
                    }
                }
                HorizontalPager(state = pagerState, count = state.pages.size) { pageIndex ->
                    val page = state.pages[pageIndex]

                    val name = remember { mutableStateOf(page.name) }
                    val amount = remember { mutableStateOf(page.amount) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        HorizontalSpacer(height = 32.dp)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                                    .clickable { es.send(CategoryManageEvent.IconSelectClick) }
                            ) {
                                Image(
                                    modifier = Modifier.align(Alignment.Center),
                                    imageVector = page.icon.getImageVector(),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
                                )
                            }
                            CapitalTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterVertically),
                                value = name.value,
                                placeholder = stringResource(id = R.string.enter_name_hint),
                                onValueChange = { name.value = it },
                                textColor = CapitalTheme.colors.onBackground
                            )
                        }
                        HorizontalSpacer(height = 24.dp)
                        AmountTextField(
                            modifier = Modifier.fillMaxWidth(),
                            amount = amount.value,
                            placeholder = stringResource(id = R.string.enter_amount_hint),
                            onValueChange = { amount.value = it },
                            textColor = CapitalTheme.colors.onBackground
                        )
                        HorizontalSpacer(height = 24.dp)
                        ArrowSettingBox(
                            modifier = Modifier.fillMaxWidth(),
                            title = "${page.currency.name} ${page.currency.name.formatCurrencySymbol()}",
                            subtitle = page.currency.name.formatCurrencyName()
                        ) {
                            es.send(CategoryManageEvent.CurrencySelectClick)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            ) {
                CapitalButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    text = stringResource(id = R.string.create_new_category),
                    onClick = { }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetContent(bottomSheet: CategoryManageBottomSheet?, es: EventSender<CategoryManageEvent>) {
    when (bottomSheet) {
        is CategoryManageBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { es.send(CategoryManageEvent.CurrencySelect(it)) }
            )
        }
        is CategoryManageBottomSheet.Icons -> {
            IconsBottomSheet(
                title = stringResource(id = R.string.select_icon),
                data = bottomSheet.data,
                onIconSelect = { es.send(CategoryManageEvent.IconSelect(it)) }
            )
        }
        else -> {}
    }
}

@Composable
private fun ExpenseCategoryAddTopBar(es: EventSender<CategoryManageEvent>) {
    Toolbar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.new_category),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(
                imageVector = CapitalIcons.ArrowLeft,
                onClick = { es.send(CategoryManageEvent.BlackClick) }
            )
        }
    )
}

@Preview
@Composable
private fun ContentLight(@PreviewParameter(CategoryManageStateProvider::class) state: CategoryManageState) {
    ComposablePreview {
        Content(state = state, MockEventSender())
    }
}

@Preview
@Composable
private fun ContentDark(@PreviewParameter(CategoryManageStateProvider::class) state: CategoryManageState) {
    ComposablePreview(isDark = true) {
        Content(state = state, MockEventSender())
    }
}
