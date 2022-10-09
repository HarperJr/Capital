package com.harper.capital.shelter.v2.ui

import com.harper.capital.shelter.ShelterScreens
import com.harper.capital.shelter.v2.ComposableComponent
import com.harper.capital.shelter.v2.SubComposableComponent
import com.harper.capital.shelter.v2.ViewModelComposableComponent
import com.harper.capital.shelter.v2.domain.ExampleInteractor
import com.harper.capital.shelter.v2.ui.model.ExampleEvent
import com.harper.capital.shelter.v2.ui.model.ExampleRouteEvent
import com.harper.capital.shelter.v2.ui.model.ExampleState
import com.harper.capital.shelter.v2.ui.model.SubExampleEvent
import com.harper.capital.shelter.v2.ui.model.SubExampleState
import kotlinx.coroutines.launch

internal class ExampleViewModel :
    ViewModelComposableComponent<ExampleState, ExampleEvent, ExampleRouteEvent, ExampleStateManager>(ShelterScreens.EXAMPLE) {
    private val interactor: ExampleInteractor by scope.inject()
    val subComponent: ProductsComponent by lazy { ProductsComponent(this) }

    override fun initStateManger(): ExampleStateManager = scope.get()

    override fun onEvent(event: ExampleEvent) {
        when (event) {
            ExampleEvent.Init -> onInit()
            ExampleEvent.BackClick -> postRouteEvent(ExampleRouteEvent.Back)
        }
    }

    private fun onInit() {
        launch {
            interactor.getDataList()
                .collect { postUiState(stateManager.update(it)) }
        }
        launchWithError(onError = { throw it }) {
            withLoading { interactor.fetchDataList() }
        }
    }
}

internal class ProductsComponent(parent: ComposableComponent<*, *>) :
    SubComposableComponent<SubExampleState, SubExampleEvent, SubExampleStateManager>(parent) {

    override fun initStateManger(): SubExampleStateManager = scope.get()

    override fun onEvent(event: SubExampleEvent) {

    }
}
