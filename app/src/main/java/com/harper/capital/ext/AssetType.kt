package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.AssetType

@Composable
fun AssetType.getText(): String = when (this) {
    AssetType.DEFAULT -> stringResource(id = R.string.type_debet)
    AssetType.CREDIT -> stringResource(id = R.string.type_credit)
    AssetType.GOAL -> stringResource(id = R.string.type_goal)
}
