package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CAmountText
import com.harper.core.component.CPreview
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetCard(modifier: Modifier = Modifier, account: Account) {
    val cardBackgroundColor = accountBackgroundColor(account.color)
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = cardBackgroundColor,
        contentColor = accountContentColorFor(cardBackgroundColor),
        elevation = 0.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(0.5f),
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
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(parent.top, margin = 8.dp)
                    },
                imageVector = account.icon.getImageVector(),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = accountContentColorFor(cardBackgroundColor))
            )
            CAmountText(
                modifier = Modifier
                    .constrainAs(amount) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    },
                amount = account.balance,
                currencyIso = account.currency.name,
                style = CapitalTheme.typography.header
            )

            val metadata = remember { account.metadata }
            val (type, info) = when (metadata) {
                is AccountMetadata.LoanAsset -> {
                    stringResource(id = R.string.credit_card) to
                        (account.balance - metadata.limit).formatWithCurrencySymbol(account.currency.name)
                }
                is AccountMetadata.GoalAsset -> {
                    stringResource(id = R.string.goal) to
                        metadata.goal.formatWithCurrencySymbol(account.currency.name)
                }
                else -> null to null
            }

            if (type != null && info != null) {
                MetadataBlock(
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(amount.bottom, margin = 4.dp)
                            start.linkTo(amount.start)
                        },
                    type = type,
                    info = info
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                text = account.name
            )
        }
    }
}

@Composable
fun MetadataBlock(modifier: Modifier = Modifier, type: String, info: String) {
    Column(modifier = modifier) {
        Text(
            text = type,
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
                    metadata = AccountMetadata.GoalAsset(goal = 100000.00)
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
                    metadata = AccountMetadata.GoalAsset(goal = 100000.00)
                )
            )
        }
    }
}
