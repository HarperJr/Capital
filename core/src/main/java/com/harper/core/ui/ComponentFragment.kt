package com.harper.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.harper.core.ext.cast
import com.harper.core.ext.tryCast
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

abstract class ComponentFragment<VM : ComponentViewModel<*>> : Fragment() {
    protected val scope: Scope by fragmentScope()
    protected abstract val viewModel: VM

    protected abstract fun content(): @Composable () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context)
        .apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setContent {
                content().invoke()
            }
        }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    protected inline fun <reified VM : ViewModel> injectViewModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ) = scope.viewModel<VM>(
        qualifier = qualifier,
        parameters = parameters,
        owner = { ViewModelOwner(viewModelStore) })

    protected inline fun <reified T : Any> requireArg(key: String): Lazy<T> =
        lazy { requireArguments().get(key).cast() }

    protected inline fun <reified T : Any> arg(key: String): Lazy<T?> =
        lazy { arguments?.get(key).tryCast() }
}

fun <T : Fragment> T.withArgs(vararg args: Pair<String, Any?>): T =
    this.apply { arguments = bundleOf(*args) }
