package com.harper.capital.main.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountGradientBackgroundColor
import com.harper.capital.ext.resolveTitle
import com.harper.capital.main.domain.model.Summary
import com.harper.core.component.ProgressChunk
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalTheme

data class MainState(
    val summary: Summary = Summary(expenses = 0.0, balance = 0.0),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = true,
    val bottomSheetState: MainBottomSheetState = MainBottomSheetState(isExpended = false)
) {
    val actionCards: List<ActionCard>
        @Composable
        get() = createActionsCards()

    @Composable
    private fun createActionsCards(): List<ActionCard> =
        ActionCardType.values().map {
            ActionCard(it, resolveActionCardBackgroundColor(it), resolveActionCardIconColor(it), it.resolveTitle())
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

private fun resolveActionCardBackgroundColor(type: ActionCardType): Brush {
    val accountColor = when (type) {
        ActionCardType.ACCOUNTS -> AccountColor.VTB_OLD
        ActionCardType.ANALYTICS_BALANCE -> AccountColor.VTB
        ActionCardType.ANALYTICS_INCOME -> AccountColor.SBER
        ActionCardType.ANALYTICS_LIABILITY -> AccountColor.ALPHA
        ActionCardType.ANALYTICS_INCOME_LIABILITY -> AccountColor.TINKOFF_PLATINUM
    }
    return accountGradientBackgroundColor(accountColor)
}

private fun resolveActionCardIconColor(type: ActionCardType): Color {
    val accountColor = when (type) {
        ActionCardType.ACCOUNTS -> AccountColor.VTB_OLD
        ActionCardType.ANALYTICS_BALANCE -> AccountColor.VTB
        ActionCardType.ANALYTICS_INCOME -> AccountColor.SBER
        ActionCardType.ANALYTICS_LIABILITY -> AccountColor.ALPHA
        ActionCardType.ANALYTICS_INCOME_LIABILITY -> AccountColor.TINKOFF_PLATINUM
    }
    return accountBackgroundColor(accountColor)
}
