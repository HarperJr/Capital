package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harper.capital.R
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.accountGradientBackgroundColor
import com.harper.capital.ext.getImageVector
import com.harper.capital.ext.resolveText
import com.harper.core.component.CAmountText
import com.harper.core.component.CPreview
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatPercent
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetCard(modifier: Modifier = Modifier, account: Account) {
    val cardBackgroundColor = accountBackgroundColor(account.color)
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = cardBackgroundColor,
        contentColor = CapitalColors.Transparent,
        elevation = 0.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        CompositionLocalProvider(LocalContentColor provides accountContentColorFor(cardBackgroundColor)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = accountGradientBackgroundColor(account.color))
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd
                )
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (icon, name, description, amount) = createRefs()
                    Image(
                        modifier = Modifier
                            .size(CapitalTheme.dimensions.imageLarge)
                            .constrainAs(icon) {
                                end.linkTo(parent.end, margin = 12.dp)
                                top.linkTo(parent.top, margin = 12.dp)
                            },
                        imageVector = account.icon.getImageVector(),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = accountContentColorFor(cardBackgroundColor).copy(alpha = 0.5f))
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(parent.top, margin = 12.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                            },
                        text = account.name,
                        style = CapitalTheme.typography.regular
                    )
                    CAmountText(
                        modifier = Modifier
                            .constrainAs(amount) {
                                start.linkTo(parent.start, margin = 16.dp)
                                top.linkTo(name.bottom, margin = 8.dp)
                            },
                        amount = account.balance,
                        currencyIso = account.currency.name,
                        style = CapitalTheme.typography.header.copy(fontSize = 32.sp)
                    )

                    val metadata = account.metadata
                    if (metadata != null) {
                        MetadataBlock(
                            modifier = Modifier
                                .constrainAs(description) {
                                    top.linkTo(amount.bottom, margin = 4.dp)
                                    start.linkTo(amount.start)
                                },
                            account = account,
                            metadata = metadata
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetadataBlock(modifier: Modifier = Modifier, account: Account, metadata: AccountMetadata) {
    val info = remember(metadata) {
        when (metadata) {
            is AccountMetadata.Loan -> {
                val diff = metadata.limit - account.balance
                if (diff > 0.0) {
                    diff.formatWithCurrencySymbol(
                        currencyIso = account.currency.name,
                        minFractionDigits = 0
                    )
                } else {
                    ""
                }
            }
            is AccountMetadata.Goal -> {
                metadata.goal.formatWithCurrencySymbol(
                    currencyIso = account.currency.name,
                    minFractionDigits = 0
                )
            }
            is AccountMetadata.Investment -> metadata.percent.formatPercent()
            else -> ""
        }
    }
    Column(modifier = modifier) {
        Text(
            text = metadata.resolveText(),
            color = CapitalTheme.colors.textSecondary,
            style = CapitalTheme.typography.regularSmall
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = info,
            color = CapitalTheme.colors.textSecondary,
            style = CapitalTheme.typography.regularSmall
        )
    }
}

@Preview
@Composable
private fun AssetCardLight() {
    CPreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetCard(
                account = Account(
                    0L,
                    "Big house",
                    type = AccountType.ASSET,
                    color = AccountColor.TINKOFF,
                    icon = AccountIcon.TINKOFF,
                    Currency.EUR,
                    75000.00,
                    metadata = AccountMetadata.Goal(goal = 100000.00)
                )
            )
        }
    }
}

@Preview
@Composable
private fun AssetCardDark() {
    CPreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetCard(
                account = Account(
                    0L,
                    "Big house",
                    type = AccountType.ASSET,
                    color = AccountColor.TINKOFF,
                    icon = AccountIcon.TINKOFF,
                    Currency.EUR,
                    75000.00,
                    metadata = AccountMetadata.Goal(goal = 100000.00)
                )
            )
        }
    }
}
