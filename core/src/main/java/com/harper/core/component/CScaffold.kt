package com.harper.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.ui.Scaffold
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun CScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = CapitalTheme.colors.background,
        topBar = topBar,
        bottomBar = {
            Column {
                bottomBar.invoke()
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsBottomHeight(WindowInsets.navigationBars)
                )
            }
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
        scaffoldState = scaffoldState
    ) { paddingValues ->
        ScaffoldContent(modifier = modifier.padding(paddingValues)) {
            content.invoke()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CModalBottomSheetScaffold(
    modifier: Modifier = Modifier,
    sheetContent: @Composable () -> Unit,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CBottomSheet {
                sheetContent.invoke()
            }
        },
        sheetBackgroundColor = CapitalTheme.colors.background,
        sheetShape = CapitalTheme.shapes.bottomSheet
    ) {
        CScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked,
            scaffoldState = scaffoldState
        ) {
            content.invoke()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CBottomSheetScaffold(
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: BottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
    sheetPeekHeight: Dp,
    topBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = CapitalTheme.colors.background,
        sheetContent = {
            CBottomSheet {
                sheetContent.invoke(this)
            }
        },
        sheetPeekHeight = sheetPeekHeight,
        sheetBackgroundColor = CapitalTheme.colors.background,
        sheetShape = CapitalTheme.shapes.bottomSheet,
        sheetElevation = BottomSheetScaffoldDefaults.SheetElevation * 2,
        topBar = topBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        ScaffoldContent(modifier = modifier.padding(paddingValues)) {
            content.invoke()
        }
    }
}

@Composable
private fun ScaffoldContent(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val navBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current)
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val looseConstraints = if (imeHeight > 0) {
            constraints.copy(
                maxHeight = constraints.maxHeight - (imeHeight - navBarHeight).coerceIn(
                    constraints.minHeight,
                    constraints.maxHeight
                )
            )
        } else {
            constraints
        }
        val placeables = measurables.map { it.measure(looseConstraints) }
        val width = if (constraints.hasBoundedWidth) {
            placeables.maxOfOrNull { it.width }.orElse(looseConstraints.maxWidth)
        } else looseConstraints.maxWidth
        val height = if (constraints.hasBoundedHeight) {
            placeables.maxOfOrNull { it.height }.orElse(looseConstraints.maxHeight)
        } else looseConstraints.maxHeight
        layout(width, height) {
            placeables.forEach { it.place(0, 0) }
        }
    }
}
