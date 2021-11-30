package com.harper.capital.overview.asset.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.harper.capital.overview.asset.ui.model.AssetAddEvent
import com.harper.capital.overview.asset.ui.model.AssetAddState
import com.harper.capital.overview.asset.ui.model.AssetAddStateProvider
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.ActionButton
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.Toolbar
import com.harper.core.ext.cast
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import kotlinx.coroutines.launch

class AssetAddFragment : ComponentFragment<AssetAddViewModel>(), EventSender<AssetAddEvent> {
    override val viewModel: AssetAddViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        when (state) {
            is AssetAddState.Loading -> {}
            is AssetAddState.Data -> Content(state.cast(), this)
        }
    }

    companion object {

        fun newInstance(): AssetAddFragment = AssetAddFragment()
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun Content(state: AssetAddState.Data, eventSender: EventSender<AssetAddEvent>) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { AssetAddTopBar() },
        sheetContent = {
            CurrencyBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                currencies = Currency.values().toList()
            )
        },
        scaffoldState = scaffoldState,
        sheetBackgroundColor = CapitalTheme.colors.background,
        sheetElevation = 6.dp,
        sheetPeekHeight = 0.dp,
        sheetShape = CapitalTheme.shapes.extraLarge.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        )
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
                currencyIso = "USD",
                onCurrencyClick = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
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
                        isSelected = state.selectedColor == item
                    ) {
                        eventSender.event(AssetAddEvent.ColorSelect(color = item))
                    }
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
                    text = stringResource(id = R.string.add_new)
                ) {
                    eventSender.event(AssetAddEvent.Apply)
                }
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
private fun ContentLight(@PreviewParameter(AssetAddStateProvider::class) state: AssetAddState.Data) {
    ComposablePreview {
        Content(state, MockEventSender())
    }
}

@Preview
@Composable
private fun ContentDark(@PreviewParameter(AssetAddStateProvider::class) state: AssetAddState.Data) {
    ComposablePreview(isDark = true) {
        Content(state, MockEventSender())
    }
}
