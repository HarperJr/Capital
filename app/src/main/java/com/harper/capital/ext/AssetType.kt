package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.asset.model.AssetMetadataType
import com.harper.capital.domain.model.AccountMetadata

@Composable
fun AssetMetadataType.resolveText(): String = when (this) {
    AssetMetadataType.DEFAULT -> stringResource(id = R.string.default_asset)
    AssetMetadataType.LOAN -> stringResource(id = R.string.loan)
    AssetMetadataType.GOAL -> stringResource(id = R.string.goal)
    AssetMetadataType.INVESTMENT -> stringResource(id = R.string.investment)
}

@Composable
fun AccountMetadata?.resolveText(): String = when (this) {
    null -> stringResource(id = R.string.default_asset)
    is AccountMetadata.Loan -> stringResource(id = R.string.loan)
    is AccountMetadata.Goal -> stringResource(id = R.string.goal)
    is AccountMetadata.Investment -> stringResource(id = R.string.investment)
}

@Composable
fun AccountMetadata.resolveValueHint(): String = when (this) {
    is AccountMetadata.Loan -> stringResource(id = R.string.enter_limit)
    is AccountMetadata.Goal -> stringResource(id = R.string.enter_goal)
    is AccountMetadata.Investment -> stringResource(id = R.string.enter_percent)
}
