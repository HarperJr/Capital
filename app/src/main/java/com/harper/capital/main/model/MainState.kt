package com.harper.capital.main.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.alpha
import com.harper.capital.ext.debt
import com.harper.capital.ext.raiffeizen
import com.harper.capital.ext.resolveTitle
import com.harper.capital.ext.sber
import com.harper.capital.ext.vtb
import com.harper.capital.ext.vtbOld
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
            ActionCard(id = it.ordinal, color = resolveActionCardColor(it), title = it.resolveTitle())
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

private fun resolveActionCardColor(type: ActionCardType): Color = when (type) {
    ActionCardType.ACCOUNTS -> vtb
    ActionCardType.ANALYTICS_BALANCE -> alpha
    ActionCardType.ANALYTICS_INCOME -> sber
    ActionCardType.ANALYTICS_INCOME_LIABILITY -> raiffeizen
    ActionCardType.ANALYTICS_LIABILITY -> debt
}
