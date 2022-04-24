package com.harper.core.ui

sealed class Message {

    class Snackbar(val message: String, val action: String? = null, val onActionClick: (() -> Unit)? = null) : Message()

    class Dialog(
        val title: String?,
        val message: String,
        val positiveAction: String?,
        val negativeAction: String?,
        val onPositiveActionClick: (() -> Unit)? = null,
        val onNegativeActionClick: (() -> Unit)? = null
    )
}
