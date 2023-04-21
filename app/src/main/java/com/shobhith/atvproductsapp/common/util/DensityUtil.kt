package com.shobhith.atvproductsapp.common.util

import android.content.Context
import kotlin.math.roundToInt

object DensityUtil {
    fun convertDpToPixel(ctx: Context, dp: Float): Float {
        return dp * ctx.applicationContext.resources.displayMetrics.density
    }
}

