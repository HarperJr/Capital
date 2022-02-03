package com.harper.capital.asset

import com.harper.capital.R
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
import com.harper.core.ui.ComponentViewModelV1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetManageViewModel(
    private val params: AssetManageFragment.Params,
    private val router: GlobalRouter,
    private val addAssetUseCase: AddAssetUseCase,
    private val updateAssetUseCase: UpdateAssetUseCase,
    private val fetchAssetUseCase: FetchAssetUseCase
) : ComponentViewModelV1<AssetManageState, AssetManageEvent>(
    defaultState = AssetManageState(mode = params.mode)
) {

    override fun onEvent(event: AssetManageEvent) {
        when (event) {
            is AssetManageEvent.ColorSelect -> onColorSelect(event)
            is AssetManageEvent.IconSelect -> onIconSelect(event)
            is AssetManageEvent.NameChange -> onNameChange(event)
            is AssetManageEvent.AmountChange -> onAmountChange(event)
            is AssetManageEvent.CurrencySelect -> onCurrencySelect(event)
            is AssetManageEvent.AssetTypeSelect -> onAssetTypeSelect(event)
            is AssetManageEvent.IncludeAssetCheckedChange -> onIncludeAssetCheckedChange(event)
            is AssetManageEvent.ActivateAssetCheckedChange -> onActivateAssetCheckedChange(event)
            AssetManageEvent.CurrencySelectClick -> onCurrencySelectClick()
            AssetManageEvent.AssetTypeSelectClick -> onAssetTypeSelectClick()
            AssetManageEvent.IconSelectClick -> onIconSelectClick()
            AssetManageEvent.BackClick -> router.back()
            AssetManageEvent.Apply -> onApply()
        }
    }

    private fun onActivateAssetCheckedChange(event: AssetManageEvent.ActivateAssetCheckedChange) {
        update { it.copy(isArchived = event.isChecked) }
    }

    override fun onFirstStart() {
        super.onFirstStart()
        params.assetId?.let { assetId ->
            launch {
                val asset = fetchAssetUseCase(assetId)
                update {
                    it.copy(
                        name = asset.name,
                        amount = asset.balance,
                        currency = asset.currency,
                        color = asset.color,
                        icon = asset.icon,
                        assetType = asset.metadata?.assetType.orElse(it.assetType),
                        isIncluded = asset.isIncluded,
                        isArchived = asset.isArchived
                    )
                }
            }
        }
    }

    private fun onApply() {
        if (state.value.name.isEmpty()) {
            update {
                it.copy(errorMessage = R.string.asset_name_is_empty_error)
            }
            return
        }
        launch(context = Dispatchers.IO) {
            with(state.value) {
                if (mode == AssetManageMode.ADD) {
                    addAssetUseCase(name, amount, currency, color, icon, assetType, isIncluded)
                } else {
                    params.assetId?.let { assetId ->
                        updateAssetUseCase(
                            assetId,
                            name,
                            amount,
                            currency,
                            color,
                            icon,
                            assetType,
                            isIncluded,
                            isArchived
                        )
                    }
                }
            }
            withContext(context = Dispatchers.Main) {
                router.back()
            }
        }
    }

    private fun onIncludeAssetCheckedChange(event: AssetManageEvent.IncludeAssetCheckedChange) {
        update {
            it.copy(isIncluded = event.isChecked)
        }
    }

    private fun onIconSelect(event: AssetManageEvent.IconSelect) {
        val selectedIcon = AssetIcon.valueOf(event.iconName)
        update {
            it.copy(
                icon = selectedIcon,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onIconSelectClick() {
        update {
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.Icons(it.icon)
                )
            )
        }
    }

    private fun onAssetTypeSelectClick() {
        update {
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.AssetTypes(it.assetType)
                )
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetManageEvent.AssetTypeSelect) {
        val selectedAssetType = AssetType.valueOf(event.assetTypeName)
        update {
            it.copy(
                assetType = selectedAssetType,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onNameChange(event: AssetManageEvent.NameChange) {
        update {
            it.copy(name = event.name)
        }
    }

    private fun onAmountChange(event: AssetManageEvent.AmountChange) {
        update {
            it.copy(amount = event.amount)
        }
    }

    private fun onCurrencySelectClick() {
        update {
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
        update {
            it.copy(
                currency = event.currency,
                bottomSheetState = it.bottomSheetState.copy(isExpended = false)
            )
        }
    }

    private fun onColorSelect(event: AssetManageEvent.ColorSelect) {
        update {
            it.copy(color = event.color)
        }
    }
}
