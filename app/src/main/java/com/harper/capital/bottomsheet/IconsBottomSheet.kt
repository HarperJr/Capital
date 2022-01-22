package com.harper.capital.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CTextField
import com.harper.core.component.CPreview
import com.harper.core.component.CWrappedGrid
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CSeparator
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun IconsBottomSheet(
    modifier: Modifier = Modifier,
    data: IconsBottomSheetData,
    onIconSelect: (String) -> Unit
) {
    val ibsData = rememberIconsBottomSheetData(data = data)
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredIcons = remember(ibsData.icons, searchQuery.value) {
        ibsData.icons.filter {
            searchQuery.value.isEmpty() ||
                    it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        CHorizontalSpacer(height = 8.dp)
        CTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchQuery.value,
            placeholder = stringResource(id = R.string.search),
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = CapitalIcons.Search,
                    colorFilter = ColorFilter.tint(color = CapitalColors.GreyDark),
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
        CSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        CWrappedGrid(modifier = Modifier.padding(horizontal = 16.dp), columns = 4, items = filteredIcons) {
            Box(modifier = Modifier.padding(4.dp)) {
                IconItem(
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.Center),
                    icon = it.imageVector,
                    isSelected = it.name == ibsData.selectedIcon
                ) {
                    onIconSelect.invoke(it.name)
                }
            }
        }
    }
}

@Composable
private fun IconItem(modifier: Modifier = Modifier, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val selectorColor = if (isSelected) CapitalTheme.colors.secondary else CapitalColors.Transparent
    Box(
        modifier = modifier
            .background(
                color = selectorColor, shape = CircleShape
            )
            .clickable { onClick.invoke() }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
        )
    }
}

@Preview
@Composable
fun IconsBottomSheetLight() {
    CPreview {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            IconsBottomSheet(
                data = IconsBottomSheetData(
                    icons = AssetIcon.values().map { IconsBottomSheetData.Icon(it.name, it.getImageVector()) },
                    selectedIcon = AssetIcon.ALPHA.name
                ),
                onIconSelect = {}
            )
        }
    }
}

@Preview
@Composable
private fun IconsBottomSheetDark() {
    CPreview(isDark = true) {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            IconsBottomSheet(
                data = IconsBottomSheetData(
                    icons = AssetIcon.values().map { IconsBottomSheetData.Icon(it.name, it.getImageVector()) },
                    selectedIcon = AssetIcon.ALPHA.name
                ),
                onIconSelect = {}
            )
        }
    }
}

@Composable
private fun rememberIconsBottomSheetData(data: IconsBottomSheetData) = remember(data) { data }

data class IconsBottomSheetData(
    val icons: List<Icon>,
    val selectedIcon: String? = null
) {

    data class Icon(val name: String, val imageVector: ImageVector)
}
