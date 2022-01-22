package com.harper.capital.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.settings.ext.resolveText
import com.harper.capital.settings.model.SettingsBottomSheet
import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CBottomSheetScaffold
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreferenceArrow
import com.harper.core.component.CPreferenceSwitch
import com.harper.core.component.CPreview
import com.harper.core.component.Toolbar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class SettingsFragment : ComponentFragment<SettingsViewModel>(), EventSender<SettingsEvent> {
    override val viewModel: SettingsViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            SettingsScreen(viewModel, this)
        }
    }

    companion object {

        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun SettingsScreen(viewModel: ComponentViewModel<SettingsState>, es: EventSender<SettingsEvent>) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    CBottomSheetScaffold(
        sheetContent = {
            val bottomSheet = remember(state.bottomSheetState) {
                state.bottomSheetState.bottomSheet
            }
            BottomSheetContent(bottomSheet = bottomSheet, es = es)
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        topBar = { SettingsTopBar(es) },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CHorizontalSpacer(height = 24.dp)
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
            CHorizontalSpacer(height = 32.dp)
            CPreferenceArrow(
                title = stringResource(id = R.string.color_theme),
                subtitle = state.colorTheme.resolveText()
            ) {
                es.send(SettingsEvent.ColorThemeSelectClick)
            }
            CPreferenceArrow(
                title = stringResource(id = R.string.default_currency),
                subtitle = state.currency.name.formatCurrencyName()
            ) {
                es.send(SettingsEvent.CurrencySelectClick)
            }
            CPreferenceArrow(title = stringResource(id = R.string.help)) {}
            CPreferenceSwitch(title = stringResource(id = R.string.notifications)) {}
        }
    }
}

@Composable
private fun BottomSheetContent(bottomSheet: SettingsBottomSheet?, es: EventSender<SettingsEvent>) {
    when (bottomSheet) {
        is SettingsBottomSheet.ColorThemes -> {
            SelectorBottomSheet(
                data = bottomSheet.data,
                onValueSelect = {
                    es.send(SettingsEvent.ColorThemeSelect(it))
                }
            )
        }
        is SettingsBottomSheet.Currencies -> {
            CurrencyBottomSheet(
                currencies = bottomSheet.currencies,
                selectedCurrency = bottomSheet.selectedCurrency,
                onCurrencySelect = { es.send(SettingsEvent.CurrencySelectClick) }
            )
        }
        else -> {}
    }
}

@Composable
private fun SettingsTopBar(es: EventSender<SettingsEvent>) {
    Toolbar(
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
                    es.send(SettingsEvent.BackClick)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenLight() {
    CPreview(isDark = true) {
        SettingsScreen(SettingsMockViewModel(), MockEventSender())
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenDark() {
    CPreview(isDark = true) {
        SettingsScreen(SettingsMockViewModel(), MockEventSender())
    }
}
