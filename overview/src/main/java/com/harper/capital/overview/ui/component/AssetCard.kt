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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harper.capital.overview.R
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.AssetMetadata
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.ComposablePreview
import com.harper.core.ext.format
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
            Text(
                modifier = Modifier
                    .constrainAs(amount) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    },
                text = buildAnnotatedString {
                    val text = asset.amount.format(asset.currency.name)
                    val commaIndex = text.indexOf(',')
                    append(text)
                    addStyle(SpanStyle(fontSize = 24.sp), start = 0, end = commaIndex)
                    addStyle(SpanStyle(fontSize = 14.sp), start = commaIndex, end = text.length - 1)
                    addStyle(
                        SpanStyle(fontSize = 24.sp),
                        start = text.length - 1,
                        end = text.length
                    )
                },
                color = CapitalColors.White,
                style = CapitalTheme.typography.regular
            )

            val metadata = remember { asset.metadata }
            if (metadata is AssetMetadata.Credit) {
                MetadataBlock(
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(amount.bottom)
                            start.linkTo(amount.start)
                        },
                    type = stringResource(id = R.string.credit_card),
                    info = (asset.amount - metadata.limit).format(asset.currency.name)
                )
            }
            if (metadata is AssetMetadata.Goal) {
                MetadataBlock(
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(amount.bottom)
                            start.linkTo(amount.start)
                        },
                    type = stringResource(id = R.string.goal),
                    info = metadata.goal.format(asset.currency.name)
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