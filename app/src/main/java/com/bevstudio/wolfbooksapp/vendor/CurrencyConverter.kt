package com.bevstudio.wolfbooksapp.vendor

/**
 * Created by Beverly May Castillo on 07/20/2021
 */
object CurrencyConverter {
    fun convertCurrency(currency: String): String {
        val symbol = when (currency) {
            "AMD" -> "Դ"
            "AOA" -> "Kz"
            "AFN" -> "Af"
            "ALL" -> "L"
            "AWG" -> "ƒ"
            "AZN" -> "ман"
            "ARS", "AUD", "BMD", "BND", "BSD", "BZD", "CAD", "CLP", "COP", "CUP", "CVE", "DOP", "FJD", "GYD", "HKD", "JMD", "KYD", "LRD", "MXN", "NAD", "NZD", "SGD", "SRD", "TTD", "TWD", "USD", "UYU", "XCD", "ZWL" -> "$"
            "PHP" -> "₱"
            "JPY" -> "¥"
            "KRW", "KPW" -> "₩"
            "LKR", "PKR", "SCR" -> "Rs"
            "TZS" -> "Sh"
            else -> currency
        }
        return symbol
    }
}
