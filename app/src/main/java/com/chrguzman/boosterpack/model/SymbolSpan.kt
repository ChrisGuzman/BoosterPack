package com.chrguzman.boosterpack.model

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.chrguzman.boosterpack.R


val symbolMap = mapOf(
    "T" to R.drawable.ic_tap,
    "Q" to R.drawable.ic_untap,
    "E" to R.drawable.ic_e,
    "PW" to R.drawable.ic_planeswalker,
    "CHAOS" to R.drawable.ic_chaos,
    "A" to R.drawable.ic_acorn,
    "X" to R.drawable.ic_x,
    "Y" to R.drawable.ic_y,
    "Z" to R.drawable.ic_z,
    "0" to R.drawable.ic_0,
    "½" to R.drawable.ic_half,
    "1" to R.drawable.ic_1,
    "2" to R.drawable.ic_2,
    "3" to R.drawable.ic_3,
    "4" to R.drawable.ic_4,
    "5" to R.drawable.ic_5,
    "6" to R.drawable.ic_6,
    "7" to R.drawable.ic_7,
    "8" to R.drawable.ic_8,
    "9" to R.drawable.ic_9,
    "10" to R.drawable.ic_10,
    "11" to R.drawable.ic_11,
    "12" to R.drawable.ic_12,
    "13" to R.drawable.ic_13,
    "14" to R.drawable.ic_14,
    "15" to R.drawable.ic_15,
    "16" to R.drawable.ic_16,
    "17" to R.drawable.ic_17,
    "18" to R.drawable.ic_18,
    "19" to R.drawable.ic_19,
    "20" to R.drawable.ic_20,
    "100" to R.drawable.ic__100,
    "1000000" to R.drawable.ic_1mil,
    "∞" to R.drawable.ic_infinity,
    "W/U" to R.drawable.ic_hybrid_mana_white_or_blue,
    "W/B" to R.drawable.ic_hybrid_mana_white_or_black,
    "B/R" to R.drawable.ic_hybrid_mana_black_or_red,
    "B/G" to R.drawable.ic_hybrid_mana_black_or_green,
    "U/B" to R.drawable.ic_hybrid_mana_blue_or_black,
    "U/R" to R.drawable.ic_hybrid_mana_blue_or_red,
    "R/G" to R.drawable.ic_hybrid_mana_red_or_green,
    "R/W" to R.drawable.ic_hybrid_mana_red_or_white,
    "G/W" to R.drawable.ic_hybrid_mana_green_or_white,
    "G/U" to R.drawable.ic_hybrid_mana_green_or_blue,
    "B/G/P" to R.drawable.ic_phyrex_green_black,
    "B/R/P" to R.drawable.ic_phyrex_red_black,
    "G/U/P" to R.drawable.ic_phyrex_green_blue,
    "G/W/P" to R.drawable.ic_phyrex_white_green,
    "R/G/P" to R.drawable.ic_phyrex_red_green,
    "R/W/P" to R.drawable.ic_phyrex_white_red,
    "U/B/P" to R.drawable.ic_phyrex_blue_black,
    "U/R/P" to R.drawable.ic_phyrex_red_blue,
    "W/B/P" to R.drawable.ic_phyrex_white_black,
    "W/U/P" to R.drawable.ic_phyrex_white_blue,
    "2/W" to R.drawable.ic_hybrid_mana_2_colorless_or_white,
    "2/U" to R.drawable.ic_hybrid_mana_2_colorless_or_blue,
    "2/B" to R.drawable.ic_hybrid_mana_2_colorless_or_black,
    "2/R" to R.drawable.ic_hybrid_mana_2_colorless_or_red,
    "2/G" to R.drawable.ic_hybrid_mana_2_colorless_or_green,
    "P" to R.drawable.ic_phyrexian_mana_colorless,
    "W/P" to R.drawable.ic_phyrexian_mana_white,
    "U/P" to R.drawable.ic_phyrexian_mana_black,
    "B/P" to R.drawable.ic_phyrexian_mana_blue,
    "R/P" to R.drawable.ic_phyrexian_mana_red,
    "G/P" to R.drawable.ic_phyrexian_mana_green,
    "HW" to  R.drawable.ic_half_white,
    "HR" to R.drawable.ic_half_red,
    "W" to R.drawable.ic_white,
    "U" to R.drawable.ic_blue,
    "B" to R.drawable.ic_black,
    "R" to R.drawable.ic_red,
    "G" to R.drawable.ic_green,
    "C" to R.drawable.ic_c,
    "S" to R.drawable.ic_snow,
)

fun String.asManaCostSpan(context: Context): SpannableStringBuilder = toSpan(context, true)
fun String.asTextSpan(context: Context): SpannableStringBuilder = toSpan(context, false)

fun String.toSpan(context: Context, isHeader: Boolean): SpannableStringBuilder {
    val size = if (isHeader) {
        48
    } else {
        32
    }
    val sb = SpannableStringBuilder("$this ")
    var i = 0
    while (i < this.length) {
        val c = this[i]
        if (c == '{') {
            var j = i
            while (j < this.length && this[j] != '}') {
                j++
            }
            @DrawableRes
            val symbol: Int = symbolMap.getOrDefault(this.substring(i + 1, j), R.drawable.ic_planeswalker)
            val d = ContextCompat.getDrawable(context, symbol)
            d?.setBounds(0, 0, size, size)
            val span = ImageSpan(d!!, ImageSpan.ALIGN_BASELINE)
            sb.setSpan(span, i, j+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            i = j
        }
        i++
    }
    return sb
}