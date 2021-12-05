package com.harper.capital.overview.asset.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.R
import com.harper.capital.overview.asset.ui.component.AssetEditableCard
import com.harper.capital.overview.asset.ui.component.ColorSelectableCircle
import com.harper.capital.overview.asset.ui.component.CurrencyBottomSheet
import com.harper.capital.overview.asset.ui.component.IconsBottomSheet
import com.harper.capital.overview.asset.ui.model.AssetAddEvent
import com.harper.capital.overview.asset.ui.model.AssetAddEventBottomSheet
import com.harper.capital.overview.asset.ui.model.AssetAddState
import com.harper.capital.overview.asset.ui.model.AssetAddStateProvider
import com.harper.capital.spec.domain.AssetIcon
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.ActionButton
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.Toolbar
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class AssetAddFragment : ComponentFragment<AssetAddViewModel>(), EventSender<AssetAddEvent> {
    override val viewModel: AssetAddViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        Content(state, this)
    }

    companion object {

        fun newInstance(): AssetAddFragment = AssetAddFragment()
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun Content(state: AssetAddState, es: EventSender<AssetAddEvent>) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(state.isBottomSheetExpendedEvent) {
        if (state.isBottomSheetExpendedEvent.value) {
            scaffoldState.bottomSheetState.expand()
        } else {
            scaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        topBar = { AssetAddTopBar() },
        sheetContent = {
            when (state.bottomSheet) {
                AssetAddEventBottomSheet.SELECT_CURRENCY -> {
                    CurrencyBottomSheet(
                        modifier = Modifier.fillMaxHeight(),
                        currencies = Currency.values().toList(),
                        selectedCurrency = state.currency,
                        onCurrencySelect = { es.send(AssetAddEvent.CurrencySelect(it)) }
                    )
                }
                AssetAddEventBottomSheet.SELECT_ICON -> {
                    IconsBottomSheet(
                        modifier = Modifier.fillMaxHeight(),
                        icons = AssetIcon.values().toList(),
                        selectedIcon = state.icon,
                        onIconSelect = { es.send(AssetAddEvent.IconSelect(it)) }
                    )
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetBackgroundColor = CapitalTheme.colors.background,
        sheetElevation = 6.dp,
        sheetPeekHeight = 0.dp,
        sheetShape = CapitalTheme.shapes.bottomSheet
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CapitalTheme.colors.background)
        ) {
            AssetEditableCard(
                modifier = Modifier
                    .assetCardSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                name = state.name,
                amount = state.amount,
                currency = state.currency,
                icon = state.icon,
                color = state.color,
                onCurrencyClick = { es.send(AssetAddEvent.CurrencySelectClick) },
                onNameChange = { es.send(AssetAddEvent.NameChange(it)) },
                onAmountChange = { es.send(AssetAddEvent.AmountChange(it)) },
                onIconClick = { es.send(AssetAddEvent.IconSelectClick) }
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                itemsIndexed(state.colors) { index, item ->
                    ColorSelectableCircle(
                        color = item,
                        isFirst = index == 0,
                        isLast = index == state.colors.size - 1,
                        isSelected = state.color == item,
                        onSelect = { es.send(AssetAddEvent.ColorSelect(color = item)) }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f, fill = true)
            ) {
                ActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    text = stringResource(id = R.string.add_new),
                    onClick = { es.send(AssetAddEvent.Apply) }
                )
            }
        }
    }
}

@Composable
private fun AssetAddTopBar() {
    Toolbar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.add_asset_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(imageVector = CapitalIcons.ArrowBack)
        }
    )
}

@Preview
@Composable
private fun ContentLight(@PreviewParameter(AssetAddStateProvider::class) state: AssetAddState) {
    ComposablePreview {
        Content(state, MockEventSender())
    }
}

@Preview
@Composable
private fun ContentDark(@PreviewParameter(AssetAddStateProvider::class) state: AssetAddState) {
    ComposablePreview(isDark = true) {
        Content(state, MockEventSender())
    }
}
