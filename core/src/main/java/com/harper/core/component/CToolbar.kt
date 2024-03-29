package com.harper.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun CToolbar(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)? = null,
    navigation: @Composable (() -> Unit)? = null,
    menu: Menu = Menu(),
    onMenuItemClick: ((Int) -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        elevation = 0.dp,
        contentColor = CapitalTheme.colors.onPrimary,
        backgroundColor = CapitalColors.Transparent,
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            navigation?.invoke()
            Box(modifier = Modifier.weight(1f)) {
                content?.invoke(this)
            }
            Row(horizontalArrangement = Arrangement.End) {
                menu.items.forEach { item ->
                    MenuItem(item, onClick = { onMenuItemClick?.invoke(it) })
                }
            }
        }
    }
}

@Composable
fun CToolbarCommon(
    modifier: Modifier = Modifier,
    title: String,
    menu: Menu = Menu(),
    onNavigationClick: () -> Unit,
    onMenuItemClick: ((Int) -> Unit)? = null
) {
    CToolbar(
        modifier = modifier,
        content = {
            Text(
                text = title,
                style = CapitalTheme.typography.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft) {
                onNavigationClick.invoke()
            }
        },
        menu = menu,
        onMenuItemClick = onMenuItemClick
    )
}

@Composable
private fun MenuItem(menuItem: MenuItem, onClick: (Int) -> Unit) {
    CIcon(imageVector = menuItem.imageVector, onClick = { onClick.invoke(menuItem.id) })
}

data class Menu(val items: List<MenuItem> = emptyList())

data class MenuItem(val id: Int, val imageVector: ImageVector)

@Preview
@Composable
private fun ToolbarLight() {
    CPreview {
        CToolbar(
            content = {
                Text(
                    text = "Capital toolbar",
                    style = CapitalTheme.typography.title,
                    color = CapitalTheme.colors.onBackground
                )
            },
            navigation = {
                CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = { })
            },
            menu = Menu(
                listOf(
                    MenuItem(0, Icons.Rounded.Info),
                    MenuItem(1, Icons.Rounded.Edit)
                )
            )
        )
    }
}

@Preview
@Composable
private fun ToolbarDark() {
    CPreview(isDark = true) {
        CToolbar(
            content = {
                Text(
                    text = "Capital toolbar",
                    style = CapitalTheme.typography.title,
                    color = CapitalTheme.colors.onBackground
                )
            },
            navigation = {
                CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = { })
            },
            menu = Menu(
                listOf(
                    MenuItem(0, Icons.Rounded.Info),
                    MenuItem(1, Icons.Rounded.Edit)
                )
            )
        )
    }
}
