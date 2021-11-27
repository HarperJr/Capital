package com.harper.capital.overview.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harper.capital.overview.R
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.AssetMetadata
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.AmountText
import com.harper.core.component.ComposablePreview
import com.harper.core.ext.formatCurrency
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetCard(
    modifier: Modifier = Modifier,
    asset: Asset
) {
    Card(
        modifier = modifier.size(width = 264.dp, height = 160.dp),
        backgroundColor = CapitalColors.DarkNight,
        elevation = 4.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (icon, name, description, amount) = createRefs()
            Image(
                modifier = Modifier
                    .constrainAs(icon) {
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(parent.top, margin = 8.dp)
                    },
                imageVector = CapitalIcons.Bank.Tinkoff,
                contentDescription = null
            )
            AmountText(
                modifier = Modifier
                    .constrainAs(amount) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    },
                text = asset.amount.formatCurrency(asset.currency.name),
                color = CapitalColors.White,
                style = CapitalTheme.typography.regular
            )

            val metadata = remember { asset.metadata }
            val (type, info) = when (metadata) {
                is AssetMetadata.Credit -> {
                    stringResource(id = R.string.credit_card) to
                            (asset.amount - metadata.limit).formatCurrency(asset.currency.name)
                }
                is AssetMetadata.Goal -> {
                    stringResource(id = R.string.goal) to
                            metadata.goal.formatCurrency(asset.currency.name)
                }
                else -> null to null
            }

            if (type != null && info != null) {
                MetadataBlock(
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(amount.bottom)
                            start.linkTo(amount.start)
                        },
                    type = type,
                    info = info
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                text = asset.name,
                color = CapitalColors.White,
                style = CapitalTheme.typography.regular
            )
        }
    }
}

@Composable
fun MetadataBlock(modifier: Modifier = Modifier, type: String, info: String) {
    Column(modifier = modifier) {
        Text(
            text = type,
            color = CapitalColors.Grey,
            style = CapitalTheme.typography.regularSmall
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = info,
            color = CapitalColors.White,
            style = CapitalTheme.typography.regularSmall
        )
    }
}

@Preview
@Composable
private fun AssetCardLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetCard(
                asset = Asset(
                    0L,
                    "Tinkoff Bank",
                    45000.00,
                    Currency.RUB,
                    metadata = AssetMetadata.Credit(limit = 75000.00)
                )
            )
        }
    }
}

@Preview
@Composable
private fun AssetCardDark() {
    ComposablePreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetCard(
                asset = Asset(
                    0L,
                    "Big house",
                    75000.00,
                    Currency.EUR,
                    metadata = AssetMetadata.Goal(goal = 100000.00)
                )
            )
        }
    }
}