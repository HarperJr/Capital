package com.harper.capital.asset

import com.harper.capital.asset.model.AssetManageEvent
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.asset.model.AssetManageState
import com.harper.capital.domain.model.AssetColor
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class AssetManageMockViewModel : ComponentViewModel<AssetManageState>(
    defaultState = AssetManageState(
        mode = AssetManageMode.ADD,
        name = "Tinkoff Platinum",
        amount = 12434.44,
        colors = AssetColor.values().toList(),
        color = AssetColor.TINKOFF
    )
), EventObserver<AssetManageEvent> {

    override fun onEvent(event: AssetManageEvent) {
        /**nope**/
    }
}
