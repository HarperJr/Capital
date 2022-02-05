package com.harper.capital.shelter

import androidx.lifecycle.viewModelScope
import com.harper.capital.shelter.core.ComposableViewModel
import com.harper.capital.shelter.domain.FetchAssetsUseCase
import com.harper.capital.shelter.model.ShelterEvent
import com.harper.capital.shelter.model.ShelterState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShelterViewModel(
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComposableViewModel<ShelterState, ShelterEvent>(ShelterState()) {

    override fun onEvent(event: ShelterEvent) {

    }

    override fun onFirstStart() {
        super.onFirstStart()
        viewModelScope.launch {
            fetchAssetsUseCase()
                .collect {
                    mutateState { prevState ->
                        prevState.copy(accounts = it)
                    }
                }
        }
    }
}
