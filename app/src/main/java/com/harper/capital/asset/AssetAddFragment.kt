package com.harper.capital.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.asset.component.AssetEditableCard
import com.harper.capital.asset.component.ColorSelectableCircle
import com.harper.capital.asset.model.AssetAddBottomSheet
import com.harper.capital.asset.model.AssetAddEvent
import com.harper.capital.asset.model.AssetAddState
import com.harper.capital.asset.model.AssetAddStateProvider
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.ext.getText
import com.harper.core.component.ActionButton
import com.harper.core.component.ArrowSettingBox
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.Separator
import com.harper.core.component.SwitchSettingBox
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
    val bottomSheet = remember(state.bottomSheetState.bottomSheet) {
        state.bottomSheetState.bottomSheet
    }

    BottomSheetScaffold(
        topBar = { AssetAddTopBar() },
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CapitalTheme.colors.background)
                .verticalScroll(rememberScrollState())
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
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(id = R.string.select_color),
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(state.colors) { item ->
                    ColorSelectableCircle(
                        color = item,
                        isSelected = state.color == item,
                        onSelect = { es.send(AssetAddEvent.ColorSelect(color = item)) }
                    )
                }
            }
            Separator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
            ArrowSettingBox(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.asset_type),
                subtitle = state.metadata.assetType.getText(),
                onClick = { es.send(AssetAddEvent.AssetTypeSelectClick) })
            SwitchSettingBox(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.include_asset),
                subtitle = stringResource(id = R.string.include_asset_subtitle),
                onCheckedChange = { es.send(AssetAddEvent.IncludeAssetCheckedChange(it)) })
            Box(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            ) {
                ActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
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
private fun BottomSheetContent(bottomSheet: AssetAddBottomSheet?, es: EventSender<AssetAddEvent>) {
    when (bottomSheet) {
        is AssetAddBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { es.send(AssetAddEvent.CurrencySelect(it)) }
            )
        }
        is AssetAddBottomSheet.Icons -> {
            IconsBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                title = stringResource(id = R.string.select_icon),
                data = bottomSheet.data,
                onIconSelect = { es.send(AssetAddEvent.IconSelect(it)) }
            )
        }
        is AssetAddBottomSheet.AssetTypes -> {
            SelectorBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                data = bottomSheet.data,
                onValueSelect = { es.send(AssetAddEvent.AssetTypeSelect(it)) }
            )
        }
        else -> {}
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
