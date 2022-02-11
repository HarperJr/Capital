package com.harper.capital.shelter.details

import com.harper.capital.shelter.core.ComposableViewModel
import com.harper.capital.shelter.model.ShelterDetailParams
import com.harper.capital.shelter.model.ShelterDetailsEvent
import com.harper.capital.shelter.model.ShelterDetailsState

class ShelterDetailsViewModel(params: ShelterDetailParams) : ComposableViewModel<ShelterDetailsState, ShelterDetailsEvent>(
    initialState = ShelterDetailsState(name = params.name)
) {

    override fun onEvent(event: ShelterDetailsEvent) {

    }
}
