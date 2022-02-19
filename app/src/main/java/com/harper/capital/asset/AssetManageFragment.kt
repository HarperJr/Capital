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
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.harper.capital.R
import com.harper.capital.asset.component.AssetColorSelector
import com.harper.capital.asset.component.AssetEditableCard
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageBottomSheetState
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
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CPreview
import com.harper.core.component.CSeparator
import com.harper.core.component.CToolbarCommon
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragmentV1
import com.harper.core.ui.ComponentViewModelV1
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

private val cardHorizontalPadding: Dp
    @Composable
    get() = CapitalTheme.dimensions.side * 2

class AssetManageFragment : ComponentFragmentV1<AssetManageViewModel>() {
    override val viewModel: AssetManageViewModel by injectViewModel { parametersOf(params) }
    private val params: Params by requireArg(PARAMS)

    @Composable
    override fun ScreenContent() {
        ScreenLayout {
            AssetManageScreen(viewModel)
        }
    }

    @Parcelize
    class Params(val mode: AssetManageMode, val accountId: Long? = null) : Parcelable

    companion object {
        private const val PARAMS = "asset_manage_params"

        fun newInstance(params: Params): AssetManageFragment =
            AssetManageFragment().withArgs(PARAMS to params)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun AssetManageScreen(viewModel: ComponentViewModelV1<AssetManageState, AssetManageEvent>) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    CBottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(state.bottomSheetState, viewModel)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    focusManager.clearFocus(force = true)
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = {
            AssetManageTopBar(
                state,
                onBackClick = { viewModel.onEvent(AssetManageEvent.BackClick) })
        },
        sheetState = sheetState,
        scaffoldState = scaffoldState
    ) {
        CLoaderLayout(isLoading = state.isLoading, loaderContent = {}) {
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
                            .padding(
                                horizontal = cardHorizontalPadding,
                                vertical = CapitalTheme.dimensions.side
                            ),
                        name = state.name,
                        balance = state.balance,
                        icon = state.icon,
                        color = state.color,
                        currency = state.currency,
                        onNameChange = { viewModel.onEvent(AssetManageEvent.NameChange(it)) },
                        onAmountChange = { viewModel.onEvent(AssetManageEvent.AmountChange(it)) },
                        onIconClick = { viewModel.onEvent(AssetManageEvent.IconSelectClick) }
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = CapitalTheme.dimensions.small),
                        horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.small),
                        contentPadding = PaddingValues(horizontal = cardHorizontalPadding)
                    ) {
                        items(state.colors) { item ->
                            AssetColorSelector(
                                color = item,
                                isSelected = state.color == item,
                                onSelect = { viewModel.onEvent(AssetManageEvent.ColorSelect(color = item)) }
                            )
                        }
                    }
                    CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                    SettingsBlock(state, viewModel)
                }
                val applyButtonText = when (state.mode) {
                    AssetManageMode.ADD -> stringResource(id = R.string.add_asset)
                    AssetManageMode.EDIT -> stringResource(id = R.string.save)
                }
                CButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(CapitalTheme.dimensions.side),
                    text = applyButtonText,
                    enabled = state.name.isNotEmpty(),
                    onClick = { viewModel.onEvent(AssetManageEvent.Apply) }
                )
            }
        }
    }
}

@Composable
private fun SettingsBlock(
    state: AssetManageState,
    viewModel: ComponentViewModelV1<AssetManageState, AssetManageEvent>
) {
    Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
        if (state.mode == AssetManageMode.ADD) {
            CPreferenceArrow(
                title = "${state.currency.name} ${state.currency.name.formatCurrencySymbol()}",
                subtitle = state.currency.name.formatCurrencyName(),
                onClick = { viewModel.onEvent(AssetManageEvent.CurrencySelectClick) })
            CSeparator()
            CPreferenceArrow(
                title = stringResource(id = R.string.asset_type),
                subtitle = state.metadataType.resolveText(),
                onClick = { viewModel.onEvent(AssetManageEvent.AssetTypeSelectClick) })
        }
        CPreferenceSwitch(
            title = stringResource(id = R.string.include_asset),
            subtitle = stringResource(id = R.string.include_asset_subtitle),
            isChecked = state.isIncluded,
            onCheckedChange = { viewModel.onEvent(AssetManageEvent.IncludeAssetCheckedChange(it)) }
        )
        if (state.mode == AssetManageMode.EDIT) {
            CPreferenceSwitch(
                title = stringResource(id = R.string.is_archived_asset),
                subtitle = stringResource(id = R.string.is_archived_asset_subtitle),
                isChecked = state.isArchived,
                onCheckedChange = { viewModel.onEvent(AssetManageEvent.ActivateAssetCheckedChange(it)) }
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    bottomSheetState: AssetManageBottomSheetState?,
    viewModel: ComponentViewModelV1<AssetManageState, AssetManageEvent>
) {
    when (val bottomSheet = bottomSheetState?.bottomSheet) {
        is AssetManageBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { viewModel.onEvent(AssetManageEvent.CurrencySelect(it)) }
            )
        }
        is AssetManageBottomSheet.Icons -> {
            IconsBottomSheet(
                data = bottomSheet.data,
                onIconSelect = { viewModel.onEvent(AssetManageEvent.IconSelect(it)) }
            )
        }
        is AssetManageBottomSheet.MetadataTypes -> {
            SelectorBottomSheet(
                data = bottomSheet.data,
                onValueSelect = { viewModel.onEvent(AssetManageEvent.AssetTypeSelect(it)) }
            )
        }
        else -> {
        }
    }
}

@Composable
private fun AssetManageTopBar(state: AssetManageState, onBackClick: () -> Unit) {
    val focusManager = LocalFocusManager.current
    val title = if (state.mode == AssetManageMode.ADD) {
        stringResource(id = R.string.new_asset_title)
    } else {
        stringResource(id = R.string.edit_asset_title)
    }
    CToolbarCommon(
        title = title,
        onNavigationClick = {
            focusManager.clearFocus(force = true)
            onBackClick.invoke()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AssetManageScreenLight() {
    CPreview {
        AssetManageScreen(AssetManageMockViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetManageScreenDark() {
    CPreview(isDark = true) {
        AssetManageScreen(AssetManageMockViewModel())
    }
}
