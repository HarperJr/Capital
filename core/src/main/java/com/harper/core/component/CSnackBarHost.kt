package com.harper.core.component

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.res.stringResource
import com.harper.core.R

@Composable
fun CSnackBarHost(
    scaffoldState: ScaffoldState,
    message: String? = null,
    onDismiss: (() -> Unit)? = null
) {
    val onDismissSnackBarState by rememberUpdatedState(newValue = onDismiss)
    val actionLabel = stringResource(id = R.string.ok)
    LaunchedEffect(message) {
        if (message != null) {
            try {
                scaffoldState.snackbarHostState.showSnackbar(
                    message, actionLabel = actionLabel
                )
            } finally {
                onDismissSnackBarState?.invoke()
            }
        }
    }
}