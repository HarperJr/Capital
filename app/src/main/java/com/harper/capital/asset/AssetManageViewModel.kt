package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.domain.UpdateAssetUseCase
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.asset.model.AssetManageBottomSheetState
import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.asset.model.AssetMetadataType
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val DEFAULT_LOAN_LIMIT = 50000.0
private const val DEFAULT_GOAL = 100000.0
private const val DEFAULT_INVESTMENT_PERCENT = 4.0

class AssetManageViewModel(
    private val params: AssetManageParams,
    private val router: GlobalRouter,
    private val addAssetUseCase: AddAssetUseCase,
    private val updateAssetUseCase: UpdateAssetUseCase,
    private val fetchAccountUseCase: FetchAccountUseCase
) : ComponentViewModel<AssetManageState, AssetManageEvent>(
    initialState = AssetManageState(mode = params.mode, isLoading = params.accountId != null)
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
            is AssetManageEvent.CurrencySelectClick -> onCurrencySelectClick()
            is AssetManageEvent.AssetTypeSelectClick -> onAssetTypeSelectClick()
            is AssetManageEvent.IconSelectClick -> onIconSelectClick()
            is AssetManageEvent.BackClick -> router.back()
            is AssetManageEvent.Apply -> onApply()
            is AssetManageEvent.MetadataValueChange -> onMetadataValueChange(event)
        }
    }

    private fun onMetadataValueChange(event: AssetManageEvent.MetadataValueChange) {
        update {
            when (it.metadata) {
                is AccountMetadata.Loan -> it.copy(metadata = it.metadata.copy(limit = event.value))
                is AccountMetadata.Goal -> it.copy(metadata = it.metadata.copy(goal = event.value))
                is AccountMetadata.Investment -> it.copy(metadata = it.metadata.copy(percent = event.value))
                else -> it
            }
        }
    }

    private fun onActivateAssetCheckedChange(event: AssetManageEvent.ActivateAssetCheckedChange) {
        update { it.copy(isArchived = event.isChecked) }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        params.accountId?.let { assetId ->
            launch {
                val asset = fetchAccountUseCase(assetId)
                update {
                    it.copy(
                        isLoading = false,
                        name = asset.name,
                        balance = asset.balance,
                        currency = asset.currency,
                        color = asset.color,
                        icon = asset.icon,
                        metadata = it.metadata,
                        isIncluded = asset.isIncluded,
                        isArchived = asset.isArchived
                    )
                }
            }
        }
    }

    private fun onApply() {
        launch(context = Dispatchers.IO) {
            with(state.value) {
                if (mode == AssetManageMode.ADD) {
                    addAssetUseCase(name, color, icon, currency, balance, isIncluded, metadata)
                } else {
                    params.accountId?.let { assetId ->
                        updateAssetUseCase(
                            assetId,
                            name,
                            balance,
                            currency,
                            color,
                            icon,
                            metadata = metadata,
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
        val selectedIcon = AccountIcon.valueOf(event.iconName)
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
            val selectedMetadataType = when (it.metadata) {
                is AccountMetadata.Loan -> AssetMetadataType.LOAN
                is AccountMetadata.Goal -> AssetMetadataType.GOAL
                is AccountMetadata.Investment -> AssetMetadataType.INVESTMENT
                else -> AssetMetadataType.DEFAULT
            }
            it.copy(
                bottomSheetState = AssetManageBottomSheetState(
                    bottomSheet = AssetManageBottomSheet.MetadataTypes(selectedMetadataType)
                )
            )
        }
    }

    private fun onAssetTypeSelect(event: AssetManageEvent.AssetTypeSelect) {
        val metadata = when (AssetMetadataType.valueOf(event.assetTypeName)) {
            AssetMetadataType.DEFAULT -> null
            AssetMetadataType.LOAN -> AccountMetadata.Loan(limit = DEFAULT_LOAN_LIMIT)
            AssetMetadataType.GOAL -> AccountMetadata.Goal(goal = DEFAULT_GOAL)
            AssetMetadataType.INVESTMENT -> AccountMetadata.Investment(percent = DEFAULT_INVESTMENT_PERCENT)
        }
        update {
            it.copy(
                metadata = metadata,
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
            it.copy(balance = event.amount)
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
