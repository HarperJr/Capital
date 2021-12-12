package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.model.AssetAddEvent
import com.harper.capital.asset.model.AssetAddEventBottomSheet
import com.harper.capital.asset.model.AssetAddState
import com.harper.capital.asset.model.BottomSheetEvent
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

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
            is AssetAddEvent.AssetTypeSelect -> onAssetTypeSelect(event)
            is AssetAddEvent.AssetTypeSelectClick -> onAssetTypeSelectClick()
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
                    metadata = metadata
                )
            }
            addAssetUseCase(asset)
            router.back()
        }
    }

    private fun onIconSelect(event: AssetAddEvent.IconSelect) {
        mutateState {
            it.copy(icon = event.icon, bottomSheetEvent = BottomSheetEvent(AssetAddEventBottomSheet.SELECT_ICON, false))
        }
    }

    private fun onIconSelectClick() {
        mutateState {
            it.copy(bottomSheetEvent = BottomSheetEvent(AssetAddEventBottomSheet.SELECT_ICON, true))
        }
    }

    private fun onAssetTypeSelectClick() {
        mutateState {
            it.copy(
                bottomSheetEvent = BottomSheetEvent(bottomSheet = AssetAddEventBottomSheet.SELECT_ASSET_TYPE, true)
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetAddEvent.AssetTypeSelect) {
        mutateState {
            val metadata = when (event.assetType) {
                AssetType.DEFAULT -> AssetMetadata.Default
                AssetType.CREDIT -> AssetMetadata.Credit(limit = 0.0)
                AssetType.GOAL -> AssetMetadata.Goal(goal = 0.0)
            }
            it.copy(
                metadata = metadata,
                bottomSheetEvent = BottomSheetEvent(AssetAddEventBottomSheet.SELECT_ASSET_TYPE, false)
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
            it.copy(bottomSheetEvent = BottomSheetEvent(AssetAddEventBottomSheet.SELECT_CURRENCY, true))
        }
    }

    private fun onCurrencySelect(event: AssetAddEvent.CurrencySelect) {
        mutateState {
            it.copy(
                currency = event.currency,
                bottomSheetEvent = BottomSheetEvent(AssetAddEventBottomSheet.SELECT_CURRENCY, false)
            )
        }
    }

    private fun onColorSelect(event: AssetAddEvent.ColorSelect) {
        mutateState {
            it.copy(color = event.color)
        }
    }
}