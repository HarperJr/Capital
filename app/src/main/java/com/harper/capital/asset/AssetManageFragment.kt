package com.harper.capital.asset

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.asset.component.AssetColorSelector
import com.harper.capital.asset.component.AssetEditableCard
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.IconsBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.ext.resolveText
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CBottomSheetScaffold
import com.harper.core.component.CButton
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CSeparator
import com.harper.core.component.CPreview
import com.harper.core.component.Toolbar
import com.harper.core.ext.compose.assetCardSize
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

class AssetManageFragment : ComponentFragment<AssetManageViewModel>(), EventSender<AssetManageEvent> {
    override val viewModel: AssetManageViewModel by injectViewModel { parametersOf(params) }
    private val params: Params by requireArg(PARAMS)

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            AssetManageScreen(viewModel, this)
        }
    }

    @Parcelize
    class Params(val mode: AssetManageMode, val assetId: Long? = null) : Parcelable

    companion object {
        private const val PARAMS = "asset_manage_params"

        fun newInstance(params: Params): AssetManageFragment =
            AssetManageFragment().withArgs(PARAMS to params)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun AssetManageScreen(viewModel: ComponentViewModel<AssetManageState>, es: EventSender<AssetManageEvent>) {
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
        topBar = { AssetManageTopBar(es) },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                CHorizontalSpacer(height = 8.dp)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    contentPadding = PaddingValues(horizontal = 32.dp)
                ) {
                    items(state.colors) { item ->
                        AssetColorSelector(
                            color = item,
                            isSelected = state.color == item,
                            onSelect = { es.send(AssetManageEvent.ColorSelect(color = item)) }
                        )
                    }
                }
                CHorizontalSpacer(height = 16.dp)
                SettingsBlock(state, es)
            }
            CButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.add_asset),
                onClick = { es.send(AssetManageEvent.Apply) }
            )
        }
    }
}

@Composable
private fun SettingsBlock(state: AssetManageState, es: EventSender<AssetManageEvent>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        CPreferenceArrow(
            title = "${state.currency.name} ${state.currency.name.formatCurrencySymbol()}",
            subtitle = state.currency.name.formatCurrencyName(),
            onClick = { es.send(AssetManageEvent.CurrencySelectClick) })
        CSeparator()
        CPreferenceArrow(
            title = stringResource(id = R.string.asset_type),
            subtitle = state.metadata.assetType.resolveText(),
            onClick = { es.send(AssetManageEvent.AssetTypeSelectClick) })
        CPreferenceSwitch(
            title = stringResource(id = R.string.include_asset),
            subtitle = stringResource(id = R.string.include_asset_subtitle),
            onCheckedChange = { es.send(AssetManageEvent.IncludeAssetCheckedChange(it)) })
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
        content = {
            Text(
                text = stringResource(id = R.string.new_asset_title),
                style = CapitalTheme.typography.title
            )
        },
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft) {
                es.send(AssetManageEvent.BackClick)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AssetManageScreenLight() {
    CPreview {
        AssetManageScreen(AssetManageMockViewModel(), MockEventSender())
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetManageScreenDark() {
    CPreview(isDark = true) {
        AssetManageScreen(AssetManageMockViewModel(), MockEventSender())
    }
}
