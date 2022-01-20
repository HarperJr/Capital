package com.harper.capital.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
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
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.ui.Scaffold
import com.harper.capital.R
import com.harper.capital.bottomsheet.CurrencyBottomSheet
import com.harper.capital.bottomsheet.SelectorBottomSheet
import com.harper.capital.settings.ext.resolveText
import com.harper.capital.settings.model.SettingsBottomSheet
import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.ArrowSettingBox
import com.harper.core.component.CapitalBottomSheet
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.MenuIcon
import com.harper.core.component.SwitchSettingBox
import com.harper.core.component.Toolbar
import com.harper.core.ext.formatCurrencyName
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

class SettingsFragment : ComponentFragment<SettingsViewModel>(), EventSender<SettingsEvent> {
    override val viewModel: SettingsViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            val state by viewModel.state.collectAsState()
            Content(state, this)
        }
    }

    companion object {

        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(state: SettingsState, es: EventSender<SettingsEvent>) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheet = remember(state.bottomSheetState.bottomSheet) {
        state.bottomSheetState.bottomSheet
    }

    ModalBottomSheetLayout(
        sheetContent = {
            CapitalBottomSheet {
                BottomSheetContent(bottomSheet = bottomSheet, es = es)
            }
            LaunchedEffect(state.bottomSheetState) {
                if (state.bottomSheetState.isExpended) {
                    sheetState.show()
                } else {
                    sheetState.hide()
                }
            }
        },
        sheetState = sheetState,
        sheetShape = CapitalTheme.shapes.bottomSheet,
        sheetBackgroundColor = CapitalTheme.colors.background
    ) {
        Scaffold(
            backgroundColor = CapitalTheme.colors.background,
            topBar = {
                SettingsTopBar(es)
            },
            bottomBar = {
                Spacer(
                    Modifier
                        .navigationBarsHeight()
                        .fillMaxWidth()
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                HorizontalSpacer(height = 24.dp)
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
                HorizontalSpacer(height = 32.dp)
                ArrowSettingBox(
                    title = stringResource(id = R.string.color_theme),
                    subtitle = state.colorTheme.resolveText()
                ) {
                    es.send(SettingsEvent.ColorThemeSelectClick)
                }
                ArrowSettingBox(
                    title = stringResource(id = R.string.default_currency),
                    subtitle = state.currency.name.formatCurrencyName()
                ) {
                    es.send(SettingsEvent.CurrencySelectClick)
                }
                ArrowSettingBox(
                    title = stringResource(id = R.string.help),
                    subtitle = ""
                ) {}
                SwitchSettingBox(
                    title = stringResource(id = R.string.notifications),
                    subtitle = ""
                ) {}
            }
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
            MenuIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                es.send(SettingsEvent.BackClick)
            })
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentLight() {
    ComposablePreview(isDark = true) {
        Content(SettingsState(), MockEventSender())
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentDark() {
    ComposablePreview(isDark = true) {
        Content(SettingsState(), MockEventSender())
    }
}
