package com.harper.capital.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.BuildConfig
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.settings.ext.resolveText
import com.harper.capital.settings.model.SettingsBottomSheet
import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CModalBottomSheetScaffold
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CPreview
import com.harper.core.component.CToolbar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SettingsScreen(viewModel: ComponentViewModel<SettingsState, SettingsEvent>) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    CModalBottomSheetScaffold(
        sheetContent = {
            val bottomSheet = remember(state.bottomSheetState) {
                state.bottomSheetState.bottomSheet
            }
            BottomSheetContent(bottomSheet = bottomSheet, viewModel)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpanded) {
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = { SettingsTopBar(viewModel) },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CapitalTheme.dimensions.side),
            verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.large)
        ) {
            Column {
                CHorizontalSpacer(height = CapitalTheme.dimensions.large)
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.email,
                        style = CapitalTheme.typography.subtitle,
                        color = CapitalColors.BlueLight
                    )
                    Text(
                        text = stringResource(id = R.string.log_out),
                        style = CapitalTheme.typography.buttonSmall,
                        color = CapitalColors.Blue
                    )
                }
            }
            CPreferenceArrow(
                title = stringResource(id = R.string.color_theme),
                subtitle = state.colorTheme.resolveText(),
                icon = { Icon(imageVector = CapitalIcons.Palette, contentDescription = null) }
            ) {
                viewModel.onEvent(SettingsEvent.ColorThemeSelectClick)
            }
            CPreferenceArrow(
                title = stringResource(id = R.string.default_currency),
                subtitle = state.currency.name.formatCurrencyName(),
                icon = {
                    Text(
                        text = state.currency.name.formatCurrencySymbol(),
                        style = CapitalTheme.typography.title,
                        textAlign = TextAlign.Center
                    )
                }
            ) {
                viewModel.onEvent(SettingsEvent.CurrencySelectClick)
            }
            CPreferenceArrow(
                title = stringResource(id = R.string.accounts_presentation),
                subtitle = stringResource(id = R.string.carousel),
                icon = { Icon(imageVector = CapitalIcons.Carousel, contentDescription = null) }
            ) {

            }
            if (BuildConfig.DEBUG) {
                CPreferenceSwitch(
                    title = stringResource(id = R.string.notifications),
                    icon = { Icon(imageVector = CapitalIcons.Notifications, contentDescription = null) }
                ) {}
                CPreferenceArrow(title = stringResource(id = R.string.help)) {}
            }
        }
    }
}

@Composable
private fun BottomSheetContent(bottomSheet: SettingsBottomSheet?, viewModel: ComponentViewModel<SettingsState, SettingsEvent>) {
    when (bottomSheet) {
        is SettingsBottomSheet.ColorThemes -> {
            SelectorBottomSheet(
                data = bottomSheet.data,
                onValueSelect = {
                    viewModel.onEvent(SettingsEvent.ColorThemeSelect(it))
                }
            )
        }
        is SettingsBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { viewModel.onEvent(SettingsEvent.CurrencySelect(it)) }
            )
        }
        else -> {
        }
    }
}

@Composable
private fun SettingsTopBar(viewModel: ComponentViewModel<SettingsState, SettingsEvent>) {
    CToolbar(
        content = {
            Text(
                text = stringResource(id = R.string.settings),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            CIcon(
                imageVector = CapitalIcons.ArrowLeft,
                onClick = {
                    viewModel.onEvent(SettingsEvent.BackClick)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenLight() {
    CPreview(isDark = false) {
        SettingsScreen(SettingsMockViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenDark() {
    CPreview(isDark = true) {
        SettingsScreen(SettingsMockViewModel())
    }
}
