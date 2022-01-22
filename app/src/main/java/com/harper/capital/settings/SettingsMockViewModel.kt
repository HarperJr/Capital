package com.harper.capital.settings

import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class SettingsMockViewModel : ComponentViewModel<SettingsState>(
    defaultState = SettingsState()
), EventObserver<SettingsEvent> {

    override fun onEvent(event: SettingsEvent) {
        /**nope**/
    }
}
