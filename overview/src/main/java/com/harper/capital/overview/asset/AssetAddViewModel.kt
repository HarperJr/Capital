package com.harper.capital.overview.asset

import com.harper.capital.overview.asset.model.AssetAddState
import com.harper.core.ui.ComponentViewModel

class AssetAddViewModel : ComponentViewModel<AssetAddState>(
    defaultState = AssetAddState.Data()
) {
}