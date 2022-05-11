package com.harper.capital.main.model

import androidx.compose.runtime.Composable
import com.harper.capital.asset.model.AssetManageBottomSheet
import com.harper.capital.domain.model.Account
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.resolveTitle
import com.harper.capital.main.domain.model.Summary
import com.harper.core.component.ProgressChunk
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalTheme

data class MainState(
    val summary: Summary = Summary(expenses = 0.0, balance = 0.0),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = true,
    val actions: List<ActionCardType> = ActionCardType.values().toList(),
    val bottomSheetState: MainBottomSheetState = MainBottomSheetState(isExpended = false)
) {
    val actionCards: List<ActionCard>
        @Composable
        get() = actions.map {
            ActionCard(id = it.ordinal, title = it.resolveTitle())
        }

    val chunks: List<ProgressChunk>
        @Composable
        get() = accounts
            .map { ProgressChunk(it.balance, color = accountBackgroundColor(it.color)) }
            .takeIf { it.isNotEmpty() }
            .orElse(listOf(ProgressChunk(1.0, color = CapitalTheme.colors.primaryVariant)))
}

data class MainBottomSheetState(
    val bottomSheet: MainBottomSheet? = null,
    val isExpended: Boolean = true
)