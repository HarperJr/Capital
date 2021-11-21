package com.harper.capital.overview.component

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.harper.capital.overview.domain.model.Asset
import com.harper.capital.overview.domain.model.Currency
import com.harper.core.component.ComposablePreview
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetCard(modifier: Modifier = Modifier, asset: Asset) {
    Card(
        modifier = modifier,
        backgroundColor = CapitalTheme.colors.primary,
        elevation = 4.dp,
        shape = CapitalTheme.shapes.large
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            val (icon, name, edit, amount) = createRefs()
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                imageVector = CapitalIcons.Wallet,
                contentDescription = null,
                tint = CapitalTheme.colors.onSecondary
            )
            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        linkTo(
                            start = icon.end,
                            end = edit.start,
                            top = icon.top,
                            bottom = icon.bottom
                        )
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 8.dp),
                text = asset.name,
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(edit) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                imageVector = CapitalIcons.Edit,
                contentDescription = null,
                tint = CapitalTheme.colors.onSecondary
            )
            Text(
                modifier = Modifier.constrainAs(amount) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
                text = formatAmountString(asset.amount, asset.currency),
                style = CapitalTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                color = CapitalTheme.colors.onPrimary
            )
        }
    }
}

fun formatAmountString(amount: Double, currency: Currency): String =
    NumberFormat.getCurrencyInstance()
        .apply { this.currency = android.icu.util.Currency.getInstance(currency.name.uppercase()) }
        .format(amount)

@Preview
@Composable
private fun AssetCardLight() {
    ComposablePreview {
        Box(modifier = Modifier.padding(16.dp)) {
            AssetCard(
                modifier = Modifier.height(128.dp),
                asset = Asset("Tinkoff Bank", 124000.00, Currency.RUR)
            )
        }
    }
}

@Preview
@Composable
private fun AssetCardDark() {
    ComposablePreview(isDark = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            AssetCard(
                modifier = Modifier.height(128.dp),
                asset = Asset("Tinkoff Bank", 124000.00, Currency.EUR)
            )
        }
    }
}