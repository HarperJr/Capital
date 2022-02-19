package com.harper.capital.settings

import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.core.ui.ComponentViewModel

class SettingsMockViewModel : ComponentViewModel<SettingsState, SettingsEvent>(
    initialState = SettingsState()
) {

    override fun onEvent(event: SettingsEvent) {
        /**nope**/
    }
}
