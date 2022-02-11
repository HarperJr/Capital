package com.harper.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.harper.core.ext.cast
import com.harper.core.ext.tryCast
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

abstract class ComponentFragmentV1<CVM : ComponentViewModelV1<*, *>> : Fragment() {
    val scope: Scope by fragmentScope()
    abstract val viewModel: CVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            ScreenContent()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    @Composable
    abstract fun ScreenContent()

    protected inline fun <reified VM : ViewModel> injectViewModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ): Lazy<VM> = lazy(mode = LazyThreadSafetyMode.NONE) {
        scope.getViewModel(
            qualifier = qualifier,
            parameters = parameters,
            owner = { ViewModelOwner(viewModelStore) }
        )
    }

    protected inline fun <reified T : Any> requireArg(key: String): Lazy<T> =
        lazy { requireArguments().get(key).cast() }

    protected inline fun <reified T : Any> arg(key: String): Lazy<T?> =
        lazy { arguments?.get(key).tryCast() }
}
