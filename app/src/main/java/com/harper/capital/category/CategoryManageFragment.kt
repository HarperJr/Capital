package com.harper.capital.category

import android.os.Parcelable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.category.model.CategoryManageBottomSheet
import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManagePage
import com.harper.capital.category.model.CategoryManageState
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.ext.getImageVector
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CBottomSheetScaffold
import com.harper.core.component.CButton
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreview
import com.harper.core.component.CTextField
import com.harper.core.component.TabBar
import com.harper.core.component.Toolbar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

class CategoryManageFragment : ComponentFragment<CategoryManageViewModel>(),
    EventSender<CategoryManageEvent> {
    override val viewModel: CategoryManageViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            CategoryManageScreen(viewModel, this)
        }
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
private fun CategoryManageScreen(
    viewModel: ComponentViewModel<CategoryManageState>,
    es: EventSender<CategoryManageEvent>
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    CBottomSheetScaffold(
        sheetContent = {
            val bottomSheet = remember(state.bottomSheetState) {
                state.bottomSheetState.bottomSheet
            }
            BottomSheetContent(bottomSheet, es)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = { CategoryManageTopBar(es) },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val pagerState = rememberPagerState(initialPage = state.selectedPage)
                TabBar(
                    data = state.tabBarData,
                    pagerState = pagerState,
                    onTabSelect = { es.send(CategoryManageEvent.TabSelect(it)) }
                )
                HorizontalPager(state = pagerState, count = state.pages.size) { pageIndex ->
                    PageBlock(page = state.pages[pageIndex], es = es)
                }
            }
            Box(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            ) {
                CButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    text = stringResource(id = R.string.create_new_category),
                    onClick = { es.send(CategoryManageEvent.Apply) }
                )
            }
        }
    }
}

@Composable
fun PageBlock(page: CategoryManagePage, es: EventSender<CategoryManageEvent>) {
    val name = remember { mutableStateOf(page.name) }
    val amount = remember { mutableStateOf(page.amount) }

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
                    .clickable { es.send(CategoryManageEvent.IconSelectClick) }
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
                value = name.value,
                placeholder = stringResource(id = R.string.enter_name_hint),
                onValueChange = {
                    name.value = it
                    es.send(CategoryManageEvent.NameChange(it))
                },
                textColor = CapitalTheme.colors.onBackground
            )
        }
        CHorizontalSpacer(height = 24.dp)
        CAmountTextField(
            modifier = Modifier.fillMaxWidth(),
            amount = amount.value,
            placeholder = stringResource(id = R.string.enter_amount_hint),
            onValueChange = {
                amount.value = it
                es.send(CategoryManageEvent.AmountChange(it))
            },
            textColor = CapitalTheme.colors.onBackground
        )
        CHorizontalSpacer(height = 24.dp)
        CPreferenceArrow(
            modifier = Modifier.fillMaxWidth(),
            title = "${page.currency.name} ${page.currency.name.formatCurrencySymbol()}",
            subtitle = page.currency.name.formatCurrencyName()
        ) {
            es.send(CategoryManageEvent.CurrencySelectClick)
        }
    }
}

@Composable
private fun BottomSheetContent(
    bottomSheet: CategoryManageBottomSheet?,
    es: EventSender<CategoryManageEvent>
) {
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
                data = bottomSheet.data,
                onIconSelect = { es.send(CategoryManageEvent.IconSelect(it)) }
            )
        }
        else -> {
        }
    }
}

@Composable
private fun CategoryManageTopBar(es: EventSender<CategoryManageEvent>) {
    Toolbar(
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
                onClick = { es.send(CategoryManageEvent.BackClick) }
            )
        }
    )
}

@Preview
@Composable
private fun CategoryManageScreenLight() {
    CPreview {
        CategoryManageScreen(CategoryManageMockViewModel(), MockEventSender())
    }
}

@Preview
@Composable
private fun CategoryManageScreenDark() {
    CPreview(isDark = true) {
        CategoryManageScreen(CategoryManageMockViewModel(), MockEventSender())
    }
}
