package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.AccountMetadataType

@Composable
fun AccountMetadataType.resolveText(): String = when (this) {
    AccountMetadataType.UNDEFINED -> stringResource(id = R.string.debet)
    AccountMetadataType.LOAN -> stringResource(id = R.string.loan)
    AccountMetadataType.GOAL -> stringResource(id = R.string.goal)
    AccountMetadataType.INVESTMENT -> stringResource(id = R.string.investment)
}
