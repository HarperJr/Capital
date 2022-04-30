package com.harper.capital.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.harper.capital.domain.model.Account
import com.harper.core.theme.CapitalTheme

private const val accountIconOffset = 0.75f
private val accountBorderWidth = 1.dp

@Composable
fun AccountRoundIconsRow(modifier: Modifier = Modifier, accounts: List<Account>) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        accounts.forEachIndexed { index, account ->
            AccountIconRound(
                modifier = Modifier
                    .padding(start = CapitalTheme.dimensions.imageMedium * index * accountIconOffset)
                    .zIndex((accounts.size - index).toFloat())
                    .border(
                        width = accountBorderWidth,
                        color = CapitalTheme.colors.background,
                        shape = CircleShape
                    ),
                size = CapitalTheme.dimensions.imageMedium + accountBorderWidth,
                color = account.color,
                icon = account.icon
            )
        }
    }
}
