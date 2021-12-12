package com.harper.capital.asset.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.AssetType
import com.harper.core.component.ComposablePreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetTypeBottomSheet(
    modifier: Modifier,
    assetTypes: List<AssetType>,
    selectedAssetType: AssetType,
    onAssetTypeSelect: (AssetType) -> Unit
) {
    Column(modifier = modifier) {
        assetTypes.forEach {
            val color = if (selectedAssetType == it) {
                CapitalColors.DodgerBlue
            } else {
                CapitalTheme.colors.onBackground
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onAssetTypeSelect.invoke(it) },
                text = resolveAssetTypeText(it),
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun resolveAssetTypeText(assetType: AssetType): String = when (assetType) {
    AssetType.DEFAULT -> stringResource(id = R.string.type_debet)
    AssetType.CREDIT -> stringResource(id = R.string.type_credit)
    AssetType.GOAL -> stringResource(id = R.string.type_goal)
}

@Preview
@Composable
private fun AssetTypeBottomSheetLight() {
    ComposablePreview {
        AssetTypeBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            assetTypes = AssetType.values().toList(),
            selectedAssetType = AssetType.CREDIT,
            onAssetTypeSelect = {}
        )
    }
}

@Preview
@Composable
private fun AssetTypeBottomSheetDark() {
    ComposablePreview {
        AssetTypeBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            assetTypes = AssetType.values().toList(),
            selectedAssetType = AssetType.DEFAULT,
            onAssetTypeSelect = {}
        )
    }
}
