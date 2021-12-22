package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.model.AssetAddBottomSheet
import com.harper.capital.asset.model.AssetAddBottomSheetState
import com.harper.capital.asset.model.AssetAddEvent
import com.harper.capital.asset.model.AssetAddState
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
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
            is AssetAddEvent.IncludeAssetCheckedChange -> onIncludeAssetCheckedChange(event)
            is AssetAddEvent.BackClick -> router.back()
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

    private fun onIncludeAssetCheckedChange(event: AssetAddEvent.IncludeAssetCheckedChange) {
        // TODO implement
    }

    private fun onIconSelect(event: AssetAddEvent.IconSelect) {
        val selectedIcon = AssetIcon.valueOf(event.iconName)
        mutateState {
            it.copy(icon = selectedIcon, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onIconSelectClick() {
        mutateState {
            it.copy(
                bottomSheetState = AssetAddBottomSheetState(
                    bottomSheet = AssetAddBottomSheet.Icons(it.icon)
                )
            )
        }
    }

    private fun onAssetTypeSelectClick() {
        mutateState {
            it.copy(
                bottomSheetState = AssetAddBottomSheetState(
                    bottomSheet = AssetAddBottomSheet.AssetTypes(it.metadata.assetType)
                )
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetAddEvent.AssetTypeSelect) {
        val selectedAssetType = AssetType.valueOf(event.assetTypeName)
        mutateState {
            val metadata = when (selectedAssetType) {
                AssetType.DEFAULT -> AssetMetadata.Default
                AssetType.CREDIT -> AssetMetadata.Credit(limit = 0.0)
                AssetType.GOAL -> AssetMetadata.Goal(goal = 0.0)
            }
            it.copy(
                metadata = metadata,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
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
                bottomSheetState = AssetAddBottomSheetState(
                    bottomSheet = AssetAddBottomSheet.Currencies(Currency.values().toList(), it.currency)
                )
            )
        }
    }

    private fun onCurrencySelect(event: AssetAddEvent.CurrencySelect) {
        mutateState {
            it.copy(
                currency = event.currency,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onColorSelect(event: AssetAddEvent.ColorSelect) {
        mutateState {
            it.copy(color = event.color)
        }
    }
}