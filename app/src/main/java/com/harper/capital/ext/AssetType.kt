package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.AssetType

@Composable
fun AssetType.resolveText(): String = when (this) {
    AssetType.DEBET -> stringResource(id = R.string.debet)
    AssetType.CREDIT -> stringResource(id = R.string.credit)
    AssetType.GOAL -> stringResource(id = R.string.goal)
    AssetType.INCOME -> stringResource(id = R.string.income)
    AssetType.EXPENSE -> stringResource(id = R.string.expense)
}
