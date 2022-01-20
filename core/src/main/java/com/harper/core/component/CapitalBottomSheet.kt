package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.harper.core.theme.CapitalColors

@Composable
fun CapitalBottomSheet(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(width = 32.dp, height = 4.dp)
                .background(color = CapitalColors.GreyLight, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        content.invoke()
        Spacer(
            modifier = Modifier
                .navigationBarsHeight()
                .fillMaxWidth()
        )
    }
}
