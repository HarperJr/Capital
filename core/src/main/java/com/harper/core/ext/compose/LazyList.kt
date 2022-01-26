package com.harper.core.ext.compose

import androidx.compose.foundation.lazy.LazyListLayoutInfo
import com.harper.core.ext.orElse
import kotlin.math.abs

fun LazyListLayoutInfo.fullyVisibleItemIndex(
    viewportCenter: LazyListLayoutInfo.() -> Float = { (viewportEndOffset + viewportStartOffset) / 2f }
): Int {
    val center = viewportCenter.invoke(this)
    return visibleItemsInfo
        .firstOrNull {
            abs((it.offset + it.size / 2f) - center) <= it.size / 2f
        }?.index.orElse(-1)
}
