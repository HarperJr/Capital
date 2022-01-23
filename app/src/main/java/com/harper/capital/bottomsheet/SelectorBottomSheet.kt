package com.harper.capital.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.AssetType
import com.harper.capital.ext.resolveText
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun SelectorBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectorBottomSheetData,
    onValueSelect: (String) -> Unit
) {
    val sbsData = rememberSelectorBottomSheetData(data)
    Column(modifier = modifier) {
        CHorizontalSpacer(height = 8.dp)
        sbsData.values.forEach {
            val contentColor =
                if (sbsData.selectedValue == it.name) CapitalTheme.colors.secondary else CapitalTheme.colors.textPrimary
            val selectorColor = if (sbsData.selectedValue == it.name)
                CapitalTheme.colors.primaryVariant else CapitalColors.Transparent
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(color = selectorColor, shape = CapitalTheme.shapes.large)
                    .clickable { onValueSelect.invoke(it.name) }
                    .padding(vertical = 8.dp),
                text = it.text,
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun AssetTypeBottomSheetLight() {
    CPreview {
        SelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            data = SelectorBottomSheetData(
                values = AssetType.values()
                    .map { SelectorBottomSheetData.Value(it.name, it.resolveText()) },
                selectedValue = AssetType.CREDIT.name
            ),
            onValueSelect = {}
        )
    }
}

@Preview
@Composable
private fun AssetTypeBottomSheetDark() {
    CPreview {
        SelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            data = SelectorBottomSheetData(
                values = AssetType.values()
                    .map { SelectorBottomSheetData.Value(it.name, it.resolveText()) },
                selectedValue = AssetType.CREDIT.name
            ),
            onValueSelect = {}
        )
    }
}

@Composable
private fun rememberSelectorBottomSheetData(data: SelectorBottomSheetData) = remember(data) { data }

data class SelectorBottomSheetData(val values: List<Value>, val selectedValue: String? = null) {

    class Value(val name: String, val text: String)
}
