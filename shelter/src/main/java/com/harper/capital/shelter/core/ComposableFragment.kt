package com.harper.capital.shelter.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.harper.core.ext.cast
import com.harper.core.ext.tryCast

abstract class ComposableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            ScreenContent()
        }
    }

    @Composable
    abstract fun ScreenContent()

    protected inline fun <reified T : Any> requireArg(key: String): Lazy<T> =
        lazy { requireArguments().get(key).cast() }

    protected inline fun <reified T : Any> arg(key: String): Lazy<T?> =
        lazy { arguments?.get(key).tryCast() }
}
