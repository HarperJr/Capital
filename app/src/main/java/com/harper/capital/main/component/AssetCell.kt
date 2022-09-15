package com.harper.capital.main.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.accountGradientBackgroundColor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CIcon
import com.harper.core.component.SwipeDirection
import com.harper.core.component.SwipeToRevealLayout
import com.harper.core.component.rememberSwipeToRevealLayoutState
import com.harper.core.ext.compose.ACCOUNT_CARD_SMALL_ASPECT_RATIO
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetCell(
    account: Account,
    onHistoryClick: () -> Unit,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onEditClick: (() -> Unit),
    onSwiped: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeToRevealLayoutState = rememberSwipeToRevealLayoutState(direction = SwipeDirection.RTL)
    SwipeToRevealLayout(
        modifier = modifier,
        state = swipeToRevealLayoutState,
        onRevealed = onSwiped,
        revealContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = CapitalTheme.colors.background)
                    .padding(CapitalTheme.dimensions.small),
                contentAlignment = Alignment.Center
            ) {
                CIcon(CapitalIcons.History) { onHistoryClick.invoke() }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = CapitalTheme.colors.background)
                    .padding(CapitalTheme.dimensions.small),
                contentAlignment = Alignment.Center
            ) {
                CIcon(CapitalIcons.Edit) { onEditClick.invoke() }
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CapitalTheme.colors.background)
                .padding(CapitalTheme.dimensions.side),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val cardBackgroundColor = accountBackgroundColor(account.color)
            Card(
                modifier = Modifier
                    .requiredWidth(CapitalTheme.dimensions.largest * 2)
                    .aspectRatio(ACCOUNT_CARD_SMALL_ASPECT_RATIO),
                backgroundColor = cardBackgroundColor,
                contentColor = CapitalColors.Transparent,
                shape = CapitalTheme.shapes.medium,
                elevation = 0.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = accountGradientBackgroundColor(account.color)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier.size(CapitalTheme.dimensions.largest),
                        imageVector = account.icon.getImageVector(),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = accountContentColorFor(cardBackgroundColor).copy(alpha = 0.5f))
                    )
                }
            }
            Column(modifier = Modifier.padding(start = CapitalTheme.dimensions.big)) {
                Text(text = account.name, style = CapitalTheme.typography.regularSmall, overflow = TextOverflow.Ellipsis)
                Text(
                    text = account.balance.formatWithCurrencySymbol(currencyIso = account.currency.name),
                    style = CapitalTheme.typography.subtitle
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Card(
                backgroundColor = CapitalTheme.colors.primaryVariant,
                shape = CircleShape
            ) {
                CompositionLocalProvider(LocalIndication provides rememberRipple(bounded = false)) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = CapitalTheme.dimensions.medium, vertical = CapitalTheme.dimensions.small),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.large)
                    ) {
                        CIcon(CapitalIcons.Income, modifier = Modifier.size(CapitalTheme.dimensions.large)) { onIncomeClick.invoke() }
                        CIcon(CapitalIcons.Expense, modifier = Modifier.size(CapitalTheme.dimensions.large)) { onExpenseClick.invoke() }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AssetCellPreview() {
    CapitalTheme(isSystemInDarkTheme()) {
        AssetCell(
            account = Account(
                id = 0L,
                name = "Tinkoff Black",
                type = AccountType.ASSET,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                currency = Currency.RUB,
                balance = 1000.5
            ),
            onHistoryClick = { },
            onIncomeClick = { },
            onExpenseClick = { },
            onEditClick = { },
            onSwiped = { }
        )
    }
}
