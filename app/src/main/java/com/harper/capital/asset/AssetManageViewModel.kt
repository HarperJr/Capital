package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.domain.UpdateAssetUseCase
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageBottomSheetState
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetManageViewModel(
    private val params: AssetManageFragment.Params,
    private val router: GlobalRouter,
    private val addAssetUseCase: AddAssetUseCase,
    private val updateAssetUseCase: UpdateAssetUseCase,
    private val fetchAssetUseCase: FetchAssetUseCase
) : ComponentViewModel<AssetManageState>(
    defaultState = AssetManageState(mode = params.mode)
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
            is AssetManageEvent.ActivateAssetCheckedChange -> {
            }
        }
    }

    override fun onFirstStart() {
        super.onFirstStart()
        params.assetId?.let { assetId ->
            launch {
                val asset = fetchAssetUseCase(assetId)
                mutateState {
                    it.copy(
                        name = asset.name,
                        amount = asset.amount,
                        currency = asset.currency,
                        color = asset.color,
                        icon = asset.icon,
                        assetType = asset.metadata?.assetType.orElse(it.assetType)
                    )
                }
            }
        }
    }

    private fun onApply() {
        launch(context = Dispatchers.IO) {
            with(state.value) {
                if (mode == AssetManageMode.ADD) {
                    addAssetUseCase(name, amount, currency, color, icon, assetType)
                } else {
                    params.assetId?.let { assetId ->
                        updateAssetUseCase(assetId, name, amount, currency, color, icon, assetType)
                    }
                }
            }
            withContext(context = Dispatchers.Main) {
                router.back()
            }
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
                    bottomSheet = AssetManageBottomSheet.AssetTypes(it.assetType)
                )
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetManageEvent.AssetTypeSelect) {
        val selectedAssetType = AssetType.valueOf(event.assetTypeName)
        mutateState {
            it.copy(
                assetType = selectedAssetType,
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
