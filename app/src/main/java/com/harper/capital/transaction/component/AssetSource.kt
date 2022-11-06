package com.harper.capital.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CPreview
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

private val requiredWidth = 86.dp

@Composable
fun AssetSource(
    modifier: Modifier = Modifier,
    account: Account,
    isEnabled: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDrag: (Float, Float) -> Unit
) {
    val backgroundColor = if (isSelected) CapitalTheme.colors.primaryVariant else CapitalColors.Transparent
    Column(
        modifier = modifier
            .requiredWidth(requiredWidth)
            .background(backgroundColor, shape = CapitalTheme.shapes.extraLarge)
            .clip(shape = CapitalTheme.shapes.extraLarge)
            .clickable(isEnabled) { onClick.invoke() }
            .padding(
                horizontal = CapitalTheme.dimensions.small,
                vertical = CapitalTheme.dimensions.medium
            )
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    if (change.pressed) {
                        onDrag.invoke(dragAmount.x, dragAmount.y)
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.small)
    ) {
        val accountIconContentColor = accountBackgroundColor(account.color)
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = accountIconContentColor, shape = CapitalTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = account.icon.getImageVector(),
                contentDescription = null,
                tint = accountContentColorFor(accountIconContentColor)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = account.name,
                style = CapitalTheme.typography.titleSmall,
                color = if (isEnabled) CapitalTheme.colors.textPrimary else CapitalTheme.colors.textSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = account.balance.formatWithCurrencySymbol(account.currency.name, minFractionDigits = 0),
                style = CapitalTheme.typography.regular.copy(fontSize = 10.sp),
                color = CapitalTheme.colors.textSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetSourceLight() {
    CPreview(isDark = false) {
        Row {
            AssetSourcePreview(isSelected = false, isEnabled = true)
            AssetSourcePreview(isSelected = true, isEnabled = true)
            AssetSourcePreview(isSelected = false, isEnabled = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetSourceDark() {
    CPreview(isDark = true) {
        Row {
            AssetSourcePreview(isSelected = false, isEnabled = true)
            AssetSourcePreview(isSelected = true, isEnabled = true)
            AssetSourcePreview(isSelected = false, isEnabled = false)
        }
    }
}

@Composable
private fun AssetSourcePreview(isSelected: Boolean, isEnabled: Boolean) {
    AssetSource(
        modifier = Modifier.padding(16.dp),
        account = Account(
            id = 0L,
            name = "Tinkoff Black",
            type = AccountType.ASSET,
            balance = 15000000.0,
            currency = Currency.RUB,
            color = AccountColor.TINKOFF,
            icon = AccountIcon.TINKOFF,
            metadata = null
        ),
        isEnabled = isEnabled,
        isSelected = isSelected,
        onClick = {},
        onDrag = { _, _ -> }
    )
}
