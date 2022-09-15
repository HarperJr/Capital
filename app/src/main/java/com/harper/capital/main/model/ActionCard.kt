package com.harper.capital.main.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Brush

class ActionCard(val type: ActionCardType, val backgroundColor: Brush, @DrawableRes val backgroundImageRes: Int, val title: String)
