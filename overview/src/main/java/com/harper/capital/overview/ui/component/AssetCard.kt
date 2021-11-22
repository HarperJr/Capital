package com.harper.capital.overview.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.harper.capital.overview.R
import com.harper.capital.spec.domain.Asset
import com.harper.core.component.ComposablePreview
import com.harper.core.ext.format
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors

@Composable
fun AssetCard(
    modifier: Modifier = Modifier,
    asset: Asset,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit
) {
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
            val (icon, name, edit, amount, income, expense) = createRefs()
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
                    .size(24.dp)
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
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(name.bottom, margin = 8.dp)
                },
                text = asset.amount.format(asset.currency.name),
                style = CapitalTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                color = CapitalTheme.colors.onPrimary
            )
            Button(
                modifier = Modifier.constrainAs(income) {
                    end.linkTo(expense.start, margin = 16.dp)
                    top.linkTo(expense.top)
                },
                onClick = onIncomeClick,
                colors = capitalButtonColors(),
                shape = CapitalTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.income),
                    style = CapitalTheme.typography.regular,
                    color = CapitalColors.White
                )
            }
            Button(
                modifier = Modifier.constrainAs(expense) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                onClick = onExpenseClick,
                colors = capitalButtonColors(),
                shape = CapitalTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.expense),
                    style = CapitalTheme.typography.regular,
                    color = CapitalColors.White
                )
            }
        }
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
                modifier = Modifier.height(200.dp),
                asset = Asset(
                    0L,
                    "Tinkoff Bank",
                    124000.00,
                    com.harper.capital.spec.domain.Currency.RUR
                ),
                {},
                {}
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
                modifier = Modifier.height(200.dp),
                asset = Asset(
                    0L,
                    "Tinkoff Bank",
                    124000.00,
                    com.harper.capital.spec.domain.Currency.EUR
                ),
                {},
                {}
            )
        }
    }
}