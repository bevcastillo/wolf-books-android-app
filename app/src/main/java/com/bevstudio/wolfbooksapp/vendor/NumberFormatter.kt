package com.bevstudio.wolfbooksapp.vendor

import java.text.DecimalFormat

object NumberFormatter {
    private val SUFFIX = arrayOf(
        "", "K", "M", "B", "T"
    )
    private const val MAX_LENGTH = 5

    @JvmStatic
    fun format(number: Float): String {
        val mFormat = DecimalFormat("###E00")
        var r = mFormat.format(number.toDouble())
        val numericValue1 = Character.getNumericValue(r[r.length - 1])
        val numericValue2 = Character.getNumericValue(r[r.length - 2])
        val combined = (numericValue2.toString() + "" + numericValue1).toInt()

        r = r.replace("E[0-9][0-9]".toRegex(), SUFFIX[combined / 3])

        while (r.length > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]".toRegex())) {
            r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
        }

        return r
    }
}
