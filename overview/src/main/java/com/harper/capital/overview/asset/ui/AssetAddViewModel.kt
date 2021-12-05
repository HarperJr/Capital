package com.harper.capital.overview.asset.ui

import com.harper.capital.overview.asset.domain.AddAssetUseCase
import com.harper.capital.overview.asset.ui.model.AssetAddEvent
import com.harper.capital.overview.asset.ui.model.AssetAddEventBottomSheet
import com.harper.capital.overview.asset.ui.model.AssetAddState
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.AssetMetadata
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import com.harper.core.ui.model.Event
import com.harper.core.ui.navigation.GlobalRouter

class AssetAddViewModel(
    private val router: GlobalRouter,
    private val addAssetUseCase: AddAssetUseCase
) : ComponentViewModel<AssetAddState>(
    defaultState = AssetAddState()
), EventObserver<AssetAddEvent> {

    override fun onEvent(event: AssetAddEvent) {
        when (event) {
            is AssetAddEvent.ColorSelect -> onColorSelect(event)
            is AssetAddEvent.IconSelect -> onIconSelect(event)
            is AssetAddEvent.NameChange -> onNameChange(event)
            is AssetAddEvent.AmountChange -> onAmountChange(event)
            is AssetAddEvent.CurrencySelect -> onCurrencySelect(event)
            is AssetAddEvent.CurrencySelectClick -> onCurrencySelectClick()
            is AssetAddEvent.IconSelectClick -> onIconSelectClick()
            is AssetAddEvent.Apply -> onApply()
        }
    }

    private fun onApply() {
        launch {
            val asset = with(state.value) {
                Asset(
                    id = 0L,
                    name = name,
                    amount = amount,
                    currency = currency,
                    color = color,
                    icon = icon,
                    metadata = AssetMetadata.Default
                )
            }
            addAssetUseCase(asset)
            router.back()
        }
    }

    private fun onIconSelect(event: AssetAddEvent.IconSelect) {
        mutateState {
            it.copy(
                icon = event.icon,
                isBottomSheetExpendedEvent = Event(false),
                bottomSheet = AssetAddEventBottomSheet.SELECT_ICON
            )
        }
    }

    private fun onIconSelectClick() {
        mutateState {
            it.copy(
                isBottomSheetExpendedEvent = Event(true),
                bottomSheet = AssetAddEventBottomSheet.SELECT_ICON
            )
        }
    }

    private fun onNameChange(event: AssetAddEvent.NameChange) {
        mutateState {
            it.copy(name = event.name)
        }
    }

    private fun onAmountChange(event: AssetAddEvent.AmountChange) {
        mutateState {
            it.copy(amount = event.amount)
        }
    }

    private fun onCurrencySelectClick() {
        mutateState {
            it.copy(
                isBottomSheetExpendedEvent = Event(true),
                bottomSheet = AssetAddEventBottomSheet.SELECT_CURRENCY
            )
        }
    }

    private fun onCurrencySelect(event: AssetAddEvent.CurrencySelect) {
        mutateState {
            it.copy(
                currency = event.currency,
                isBottomSheetExpendedEvent = Event(false),
                bottomSheet = AssetAddEventBottomSheet.SELECT_CURRENCY
            )

        }
    }

    private fun onColorSelect(event: AssetAddEvent.ColorSelect) {
        mutateState {
            it.copy(color = event.color)
        }
    }
}