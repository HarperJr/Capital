package com.harper.capital.bottomsheet

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
import com.harper.core.component.ComposablePreview
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
        sbsData.values.forEach {
            val color = if (sbsData.selectedValue == it.name) {
                CapitalColors.Blue
            } else {
                CapitalTheme.colors.onBackground
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onValueSelect.invoke(it.name) },
                text = it.text,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun AssetTypeBottomSheetLight() {
    ComposablePreview {
        SelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            data = SelectorBottomSheetData(
                values = AssetType.values().map { SelectorBottomSheetData.Value(it.name, it.resolveText()) },
                selectedValue = AssetType.CREDIT.name
            ),
            onValueSelect = {}
        )
    }
}

@Preview
@Composable
private fun AssetTypeBottomSheetDark() {
    ComposablePreview {
        SelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            data = SelectorBottomSheetData(
                values = AssetType.values().map { SelectorBottomSheetData.Value(it.name, it.resolveText()) },
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
