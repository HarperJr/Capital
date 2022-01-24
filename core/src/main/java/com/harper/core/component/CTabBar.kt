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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Composable
@OptIn(ExperimentalPagerApi::class)
fun TabBar(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    data: TabBarData,
    onTabSelect: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val rememberedData = rememberTabBarData(data)
    val selectedTabIndex = remember { mutableStateOf(pagerState.currentPage) }
    TabRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        backgroundColor = CapitalTheme.colors.background,
        selectedTabIndex = selectedTabIndex.value,
        indicator = {
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(it[selectedTabIndex.value])
                    .fillMaxHeight()
                    .padding(vertical = 2.dp)
                    .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                    .zIndex(-1f)
            )
        },
        divider = {}
    ) {
        rememberedData.tabs.forEachIndexed { index, tab ->
            Tab(
                modifier = Modifier.height(CapitalTheme.dimensions.largest),
                selected = index == selectedTabIndex.value,
                selectedContentColor = CapitalColors.White,
                unselectedContentColor = CapitalColors.GreyDark,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = tab.title,
                    style = CapitalTheme.typography.buttonSmall
                )
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex.value = page
            onTabSelect.invoke(page)
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
@OptIn(ExperimentalPagerApi::class)
private fun TabBarLight() {
    val tabs = listOf(
        Tab(title = "Section1"),
        Tab(title = "Section2"),
        Tab(title = "Section3"),
        Tab(title = "Section4")
    )
    CPreview {
        TabBar(data = TabBarData(tabs), pagerState = rememberPagerState(), onTabSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalPagerApi::class)
private fun TabBarDark() {
    val tabs = listOf(
        Tab(title = "Section1"),
        Tab(title = "Section2"),
        Tab(title = "Section3"),
        Tab(title = "Section4")
    )
    CPreview(isDark = true) {
        TabBar(data = TabBarData(tabs), pagerState = rememberPagerState(), onTabSelect = {})
    }
}

