package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageBottomSheetState
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class AssetManageViewModel(
    private val router: GlobalRouter,
    private val addAssetUseCase: AddAssetUseCase
) : ComponentViewModel<AssetManageState>(
    defaultState = AssetManageState()
), EventObserver<AssetManageEvent> {

    override fun onEvent(event: AssetManageEvent) {
        when (event) {
            is AssetManageEvent.ColorSelect -> onColorSelect(event)
            is AssetManageEvent.IconSelect -> onIconSelect(event)
            is AssetManageEvent.NameChange -> onNameChange(event)
            is AssetManageEvent.AmountChange -> onAmountChange(event)
            is AssetManageEvent.CurrencySelect -> onCurrencySelect(event)
            is AssetManageEvent.CurrencySelectClick -> onCurrencySelectClick()
            is AssetManageEvent.AssetTypeSelect -> onAssetTypeSelect(event)
            is AssetManageEvent.AssetTypeSelectClick -> onAssetTypeSelectClick()
            is AssetManageEvent.IconSelectClick -> onIconSelectClick()
            is AssetManageEvent.Apply -> onApply()
            is AssetManageEvent.IncludeAssetCheckedChange -> onIncludeAssetCheckedChange(event)
            is AssetManageEvent.BackClick -> router.back()
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

    private fun onIncludeAssetCheckedChange(event: AssetManageEvent.IncludeAssetCheckedChange) {
        // TODO implement
    }

    private fun onIconSelect(event: AssetManageEvent.IconSelect) {
        val selectedIcon = AssetIcon.valueOf(event.iconName)
        mutateState {
            it.copy(
                icon = selectedIcon,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onIconSelectClick() {
        mutateState {
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.Icons(it.icon)
                )
            )
        }
    }

    private fun onAssetTypeSelectClick() {
        mutateState {
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.AssetTypes(it.metadata.assetType)
                )
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetManageEvent.AssetTypeSelect) {
        val selectedAssetType = AssetType.valueOf(event.assetTypeName)
        mutateState {
            val metadata = when (selectedAssetType) {
                AssetType.DEBET -> AssetMetadata.Debet
                AssetType.CREDIT -> AssetMetadata.Credit(limit = 0.0)
                AssetType.GOAL -> AssetMetadata.Goal(goal = 0.0)
                else -> return@mutateState it
            }
            it.copy(
                metadata = metadata,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onNameChange(event: AssetManageEvent.NameChange) {
        mutateState {
            it.copy(name = event.name)
        }
    }

    private fun onAmountChange(event: AssetManageEvent.AmountChange) {
        mutateState {
            it.copy(amount = event.amount)
        }
    }

    private fun onCurrencySelectClick() {
        mutateState {
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.Currencies(
                        Currency.values().toList(),
                        it.currency
                    )
                )
            )
        }
    }

    private fun onCurrencySelect(event: AssetManageEvent.CurrencySelect) {
        mutateState {
            it.copy(
                currency = event.currency,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onColorSelect(event: AssetManageEvent.ColorSelect) {
        mutateState {
            it.copy(color = event.color)
        }
    }
}
