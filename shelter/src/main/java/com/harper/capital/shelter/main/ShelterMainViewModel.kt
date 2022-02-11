package com.harper.capital.shelter.main

import androidx.lifecycle.viewModelScope
import com.harper.capital.shelter.core.ComposableViewModel
import com.harper.capital.shelter.domain.FetchAssetsUseCase
import com.harper.capital.shelter.model.ShelterEvent
import com.harper.capital.shelter.model.ShelterState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShelterMainViewModel(
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComposableViewModel<ShelterState, ShelterEvent>(ShelterState()) {

    override fun onEvent(event: ShelterEvent) {
        when (event) {
            ShelterEvent.BackClick -> {
            }
        }
    }

    override fun onFirstCompose() {
        super.onFirstCompose()
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
