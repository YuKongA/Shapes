package com.kyant.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.util.lerp

sealed interface CornerRadius {

    context(size: Size, density: Density)
    fun toPx(): Float

    data class Px(val value: Float) : CornerRadius {

        context(size: Size, density: Density)
        override fun toPx(): Float = value
    }

    data class Dp(val value: androidx.compose.ui.unit.Dp) : CornerRadius {

        context(size: Size, density: Density)
        override fun toPx(): Float = with(density) { value.toPx() }
    }

    data class Lerp(
        val from: CornerRadius,
        val to: CornerRadius,
        val fraction: Float
    ) : CornerRadius {

        context(size: Size, density: Density)
        override fun toPx(): Float = lerp(from.toPx(), to.toPx(), fraction)
    }

    data object Zero : CornerRadius {

        context(size: Size, density: Density)
        override fun toPx(): Float = 0f
    }

    data object Max : CornerRadius {

        context(size: Size, density: Density)
        override fun toPx(): Float = size.minDimension * 0.5f
    }
}

fun lerp(start: CornerRadius, stop: CornerRadius, fraction: Float) =
    CornerRadius.Lerp(start, stop, fraction)
