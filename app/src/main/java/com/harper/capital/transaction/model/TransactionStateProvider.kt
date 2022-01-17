package com.harper.capital.transaction.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.R
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency

class TransactionStateProvider : PreviewParameterProvider<TransactionState> {
    override val values: Sequence<TransactionState>
        get() = sequenceOf(
            TransactionState(
                selectedPage = TransactionType.EXPENSE.ordinal,
                pages = listOf(
                    TransactionPage(
                        titleRes = R.string.expense,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        titleRes = R.string.income,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        titleRes = R.string.send,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        titleRes = R.string.goal,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        titleRes = R.string.duty,
                        assetDataSets = assetDataSets
                    )
                )
            )
        )
    private val assetDataSets: List<AssetDataSet>
        get() = listOf(
            AssetDataSet(
                section = DataSetSection.FROM,
                assets = listOf(
                    Asset(
                        id = 0L,
                        name = "Tinkoff Black",
                        amount = 1500.0,
                        currency = Currency.RUB,
                        color = AssetColor.DARK_TINKOFF,
                        icon = AssetIcon.TINKOFF,
                        metadata = AssetMetadata.Default
                    ),
                    Asset(
                        id = 1L,
                        name = "Sberbank",
                        amount = 2500.50,
                        currency = Currency.RUB,
                        color = AssetColor.GREEN_SBER,
                        icon = AssetIcon.SBER,
                        metadata = AssetMetadata.Default
                    ),
                    Asset(
                        id = 2L,
                        name = "Raiffeisen",
                        amount = 100000.00,
                        currency = Currency.RUB,
                        color = AssetColor.YELLOW_RAIFFEIZEN,
                        icon = AssetIcon.RAIFFEISEN,
                        metadata = AssetMetadata.Default
                    ),
                    Asset(
                        id = 3L,
                        name = "VTB USD",
                        amount = 100.50,
                        currency = Currency.USD,
                        color = AssetColor.LIGHT_BLUE_VTB,
                        icon = AssetIcon.VTB,
                        metadata = AssetMetadata.Default
                    )
                ),
                selectedAssetId = 1
            ),
            AssetDataSet(
                section = DataSetSection.TO,
                assets = listOf(
                    Asset(
                        id = 0L,
                        name = "Products",
                        amount = 1500.0,
                        currency = Currency.RUB,
                        color = AssetColor.YELLOW_RAIFFEIZEN,
                        icon = AssetIcon.BITCOIN,
                        metadata = AssetMetadata.Default
                    ),
                    Asset(
                        id = 1L,
                        name = "Phone",
                        amount = 2500.50,
                        currency = Currency.RUB,
                        color = AssetColor.DARK_TINKOFF,
                        icon = AssetIcon.ETHERIUM,
                        metadata = AssetMetadata.Default
                    )
                ),
                selectedAssetId = null
            )
        )
}
