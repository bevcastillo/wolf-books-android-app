package com.example.wolfbooksapp.vendor;

public class CurrencyConverter {
    public static String convertCurrency(String currency) {
        String symbol;
        switch (currency) {
            case "AMD":
                symbol="Դ";
                break;
            case "AOA":
                symbol="Kz";
                break;
            case "AFN":
                symbol="Af";
                break;
            case "ALL":
                symbol="L";
                break;
            case "AWG":
                symbol="ƒ";
                break;
            case "AZN":
                symbol="ман";
                break;
            case "ARS":
            case "AUD":
            case "BMD":
            case "BND":
            case "BSD":
            case "BZD":
            case "CAD":
            case "CLP":
            case "COP":
            case "CUP":
            case "CVE":
            case "DOP":
            case "FJD":
            case "GYD":
            case "HKD":
            case "JMD":
            case "KYD":
            case "LRD":
            case "MXN":
            case "NAD":
            case "NZD":
            case "SGD":
            case "SRD":
            case "TTD":
            case "TWD":
            case "USD":
            case "UYU":
            case "XCD":
            case "ZWL":
                symbol="$";
                break;
            case "PHP":
                symbol="₱";
                break;
            case "JPY":
                symbol="¥";
                break;
            case "KRW":
            case "KPW":
                symbol="₩";
                break;
            case "LKR":
            case "PKR":
            case "SCR":
                symbol="Rs";
                break;
            case "TZS":
                symbol="Sh";
                break;
            default: symbol=currency;
        }

        return symbol;
    }
}
