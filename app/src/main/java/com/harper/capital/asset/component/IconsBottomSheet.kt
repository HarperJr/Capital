package com.harper.capital.asset.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.domain.model.AssetIcon
import com.harper.core.component.CapitalTextField
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Separator
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun IconsBottomSheet(
    modifier: Modifier = Modifier,
    icons: List<AssetIcon>,
    selectedIcon: AssetIcon,
    onIconSelect: (AssetIcon) -> Unit
) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredIcons = remember(icons, searchQuery.value) {
        icons.filter {
            searchQuery.value.isEmpty() ||
                    it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.select_icon),
            style = CapitalTheme.typography.title,
            color = CapitalTheme.colors.onBackground
        )
        CapitalTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchQuery.value,
            placeholder = stringResource(id = R.string.search),
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = CapitalIcons.Search,
                    colorFilter = ColorFilter.tint(color = CapitalColors.Boulder),
                    contentDescription = null
                )
            },
            onValueChange = { searchQuery.value = it }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Separator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Layout(
            content = {
                filteredIcons.forEach {
                    IconItem(modifier = Modifier.padding(4.dp), icon = it, isSelected = it == selectedIcon) {
                        onIconSelect.invoke(it)
                    }
                }
            },
            modifier = Modifier.scrollable(rememberScrollState(), orientation = Orientation.Vertical),
            measurePolicy = { measurables, constraints ->
                val size: Int = if (constraints.hasBoundedWidth)
                    (constraints.maxWidth / 4).coerceIn(constraints.minWidth, constraints.maxWidth) else 0
                val placeables = measurables.map { it.measure(Constraints.fixed(size, size)) }
                val rows = placeables.size / 4 + if ((placeables.size % 4) > 0) 1 else 0
                val layoutHeight = rows * size
                layout(constraints.maxWidth, layoutHeight) {
                    placeables.forEachIndexed { i, placeable ->
                        val row = i / 4
                        val column = i % 4
                        placeable.placeRelative(column * placeable.width, row * placeable.height)
                    }
                }
            }
        )
    }
}

@Composable
private fun IconItem(modifier: Modifier = Modifier, icon: AssetIcon, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) {
                    CapitalTheme.colors.secondary
                } else {
                    CapitalColors.Transparent
                }, shape = CircleShape
            )
            .clickable { onClick.invoke() }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = icon.getImageVector(),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
        )
    }
}

@Composable
fun AssetIcon.getImageVector(): ImageVector = when (this) {
    AssetIcon.TINKOFF -> CapitalIcons.Bank.Tinkoff
    AssetIcon.ALPHA -> CapitalIcons.Bank.Alpha
    AssetIcon.VTB -> CapitalIcons.Bank.Vtb
    AssetIcon.SBER -> CapitalIcons.Bank.Sber
    AssetIcon.RAIFFEISEN -> CapitalIcons.Bank.Raiffeisen
    AssetIcon.BITCOIN -> CapitalIcons.Bank.Tinkoff
    AssetIcon.ETHERIUM -> CapitalIcons.Bank.Tinkoff
    AssetIcon.USD -> CapitalIcons.Bank.Tinkoff
    AssetIcon.EUR -> CapitalIcons.Bank.Tinkoff
    AssetIcon.PIGGY_BANK -> CapitalIcons.Bank.Tinkoff
}

@Preview
@Composable
fun IconsBottomSheetLight() {
    ComposablePreview {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            IconsBottomSheet(
                icons = AssetIcon.values().toList(),
                selectedIcon = AssetIcon.ALPHA,
                onIconSelect = {}
            )
        }
    }
}

@Preview
@Composable
private fun IconsBottomSheetDark() {
    ComposablePreview(isDark = true) {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            IconsBottomSheet(
                icons = AssetIcon.values().toList(),
                selectedIcon = AssetIcon.TINKOFF,
                onIconSelect = {}
            )
        }
    }
}
