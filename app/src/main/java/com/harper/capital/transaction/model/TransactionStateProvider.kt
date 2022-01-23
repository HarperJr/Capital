package com.harper.capital.transaction.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
                        type = TransactionType.EXPENSE,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        type = TransactionType.INCOME,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        type = TransactionType.SEND,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        type = TransactionType.GOAL,
                        assetDataSets = assetDataSets
                    ),
                    TransactionPage(
                        type = TransactionType.DUTY,
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
                        color = AssetColor.TINKOFF,
                        icon = AssetIcon.TINKOFF,
                        metadata = AssetMetadata.Debet
                    ),
                    Asset(
                        id = 1L,
                        name = "Sberbank",
                        amount = 2500.50,
                        currency = Currency.RUB,
                        color = AssetColor.SBER,
                        icon = AssetIcon.SBER,
                        metadata = AssetMetadata.Debet
                    ),
                    Asset(
                        id = 2L,
                        name = "Raiffeisen",
                        amount = 100000.00,
                        currency = Currency.RUB,
                        color = AssetColor.RAIFFEIZEN,
                        icon = AssetIcon.RAIFFEISEN,
                        metadata = AssetMetadata.Debet
                    ),
                    Asset(
                        id = 3L,
                        name = "VTB USD",
                        amount = 100.50,
                        currency = Currency.USD,
                        color = AssetColor.VTB,
                        icon = AssetIcon.VTB,
                        metadata = AssetMetadata.Debet
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
                        color = AssetColor.RAIFFEIZEN,
                        icon = AssetIcon.BITCOIN,
                        metadata = AssetMetadata.Debet
                    ),
                    Asset(
                        id = 1L,
                        name = "Phone",
                        amount = 2500.50,
                        currency = Currency.RUB,
                        color = AssetColor.TINKOFF,
                        icon = AssetIcon.ETHERIUM,
                        metadata = AssetMetadata.Debet
                    )
                ),
                selectedAssetId = null
            )
        )
}
