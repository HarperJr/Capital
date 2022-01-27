package com.harper.capital.transaction.manage.model

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.TransactionType
import java.time.LocalDate

typealias AssetPair = Pair<Asset, Asset>

data class TransactionManageState(
    val transactionType: TransactionType,
    val assetPair: AssetPair? = null,
    val amount: Double = 0.0,
    val date: LocalDate = LocalDate.now(),
    val comment: String? = null,
    val isScheduled: Boolean = false,
    val datePickerDialogState: DatePickerDialogState = DatePickerDialogState(isVisible = false),
    val isLoading: Boolean = true
)
