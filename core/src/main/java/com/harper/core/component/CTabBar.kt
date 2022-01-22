package com.harper.core.component

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import kotlinx.parcelize.Parcelize

@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    data: TabBarData,
    onTabSelect: (Int) -> Unit
) {
    val rememberedData = rememberTabBarData(data)
    TabRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        backgroundColor = CapitalTheme.colors.background,
        selectedTabIndex = selectedTabIndex,
        indicator = {
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(it[selectedTabIndex])
                    .padding(vertical = 2.dp)
                    .fillMaxHeight()
                    .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                    .zIndex(-1f)
            )
        },
        divider = {}
    ) {
        rememberedData.tabs.forEachIndexed { index, tab ->
            Tab(
                modifier = Modifier.height(32.dp),
                selected = index == selectedTabIndex,
                selectedContentColor = CapitalColors.White,
                unselectedContentColor = CapitalColors.GreyDark,
                onClick = { onTabSelect.invoke(index) }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = tab.title,
                    style = CapitalTheme.typography.buttonSmall
                )
            }
        }
    }
}

@Parcelize
data class TabBarData(val tabs: List<Tab>) : Parcelable

@Parcelize
data class Tab(val title: String) : Parcelable

@Composable
private fun rememberTabBarData(data: TabBarData) = rememberSaveable { data }

@Preview(showBackground = true)
@Composable
private fun TabBarLight() {
    val tabs = listOf(
        Tab(title = "Section1"),
        Tab(title = "Section2"),
        Tab(title = "Section3"),
        Tab(title = "Section4")
    )
    CPreview {
        TabBar(data = TabBarData(tabs), selectedTabIndex = 1, onTabSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun TabBarDark() {
    val tabs = listOf(
        Tab(title = "Section1"),
        Tab(title = "Section2"),
        Tab(title = "Section3"),
        Tab(title = "Section4")
    )
    CPreview(isDark = true) {
        TabBar(data = TabBarData(tabs), selectedTabIndex = 1, onTabSelect = {})
    }
}

