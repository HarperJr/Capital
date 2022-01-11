package com.harper.capital.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.harper.capital.asset.component.AssetColorSelector
import com.harper.capital.asset.component.AssetEditableCard
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.asset.model.AssetManageStateProvider
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.ext.getText
import com.harper.core.component.ArrowSettingBox
import com.harper.core.component.CapitalButton
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.MenuIcon
import com.harper.core.component.Separator
import com.harper.core.component.SwitchSettingBox
import com.harper.core.component.Toolbar
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class AssetAddFragment : ComponentFragment<AssetManageViewModel>(), EventSender<AssetManageEvent> {
    override val viewModel: AssetManageViewModel by injectViewModel()

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
private fun Content(state: AssetManageState, es: EventSender<AssetManageEvent>) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheet = remember(state.bottomSheetState.bottomSheet) {
        state.bottomSheetState.bottomSheet
    }

    BottomSheetScaffold(
        backgroundColor = CapitalTheme.colors.background,
        topBar = { AssetManageTopBar(es) },
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
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                AssetEditableCard(
                    modifier = Modifier
                        .assetCardSize()
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    name = state.name,
                    amount = state.amount,
                    icon = state.icon,
                    color = state.color,
                    onNameChange = { es.send(AssetManageEvent.NameChange(it)) },
                    onAmountChange = { es.send(AssetManageEvent.AmountChange(it)) },
                    onIconClick = { es.send(AssetManageEvent.IconSelectClick) }
                )
                HorizontalSpacer(height = 8.dp)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(state.colors) { item ->
                        AssetColorSelector(
                            color = item,
                            isSelected = state.color == item,
                            onSelect = { es.send(AssetManageEvent.ColorSelect(color = item)) }
                        )
                    }
                }
                HorizontalSpacer(height = 16.dp)
                ArrowSettingBox(
                    modifier = Modifier.fillMaxWidth(),
                    title = "${state.currency.name} ${state.currency.name.formatCurrencySymbol()}",
                    subtitle = state.currency.name.formatCurrencyName(),
                    onClick = { es.send(AssetManageEvent.CurrencySelectClick) })
                Separator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                ArrowSettingBox(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.asset_type),
                    subtitle = state.metadata.assetType.getText(),
                    onClick = { es.send(AssetManageEvent.AssetTypeSelectClick) })
                SwitchSettingBox(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.include_asset),
                    subtitle = stringResource(id = R.string.include_asset_subtitle),
                    onCheckedChange = { es.send(AssetManageEvent.IncludeAssetCheckedChange(it)) })
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
                    text = stringResource(id = R.string.add_asset),
                    onClick = { es.send(AssetManageEvent.Apply) }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetContent(bottomSheet: AssetManageBottomSheet?, es: EventSender<AssetManageEvent>) {
    when (bottomSheet) {
        is AssetManageBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { es.send(AssetManageEvent.CurrencySelect(it)) }
            )
        }
        is AssetManageBottomSheet.Icons -> {
            IconsBottomSheet(
                title = stringResource(id = R.string.select_icon),
                data = bottomSheet.data,
                onIconSelect = { es.send(AssetManageEvent.IconSelect(it)) }
            )
        }
        is AssetManageBottomSheet.AssetTypes -> {
            SelectorBottomSheet(
                data = bottomSheet.data,
                onValueSelect = { es.send(AssetManageEvent.AssetTypeSelect(it)) }
            )
        }
        else -> {}
    }
}

@Composable
private fun AssetManageTopBar(es: EventSender<AssetManageEvent>) {
    Toolbar(
        title = {
            Text(
                text = stringResource(id = R.string.new_asset_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(imageVector = CapitalIcons.ArrowLeft) {
                es.send(AssetManageEvent.BackClick)
            }
        }
    )
}

@Preview
@Composable
private fun ContentLight(@PreviewParameter(AssetManageStateProvider::class) state: AssetManageState) {
    ComposablePreview {
        Content(state, MockEventSender())
    }
}

@Preview
@Composable
private fun ContentDark(@PreviewParameter(AssetManageStateProvider::class) state: AssetManageState) {
    ComposablePreview(isDark = true) {
        Content(state, MockEventSender())
    }
}
