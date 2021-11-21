package com.harper.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalTheme

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigation: @Composable () -> Unit = {},
    menu: Menu = Menu(),
    onMenuItemClick: (Int) -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = CapitalTheme.colors.background
    ) {
        navigation.invoke()
        title.invoke()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            menu.items.forEach { item ->
                MenuItem(item, onClick = { onMenuItemClick.invoke(it) })
            }
        }
    }
}

@Composable
private fun MenuItem(menuItem: MenuItem, onClick: (Int) -> Unit) {
    MenuIcon(imageVector = menuItem.imageVector, onClick = { onClick.invoke(menuItem.id) })
}

data class Menu(val items: List<MenuItem> = emptyList())

data class MenuItem(val id: Int, val imageVector: ImageVector)

@Preview
@Composable
fun ToolbarLight() {
    ComposablePreview {
        Toolbar(
            title = {
                Text(
                    text = "Capital toolbar",
                    style = CapitalTheme.typography.title,
                    color = CapitalTheme.colors.onBackground
                )
            },
            navigation = {
                MenuIcon(imageVector = Icons.Rounded.ArrowBack, onClick = { })
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
fun ToolbarDark() {
    ComposablePreview(isDark = true) {
        Toolbar(
            title = {
                Text(
                    text = "Capital toolbar",
                    style = CapitalTheme.typography.title,
                    color = CapitalTheme.colors.onBackground
                )
            },
            navigation = {
                MenuIcon(imageVector = Icons.Rounded.ArrowBack, onClick = { })
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
