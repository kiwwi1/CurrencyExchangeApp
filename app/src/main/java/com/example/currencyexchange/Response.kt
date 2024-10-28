package com.example.currencyexchange

data class ExchangeRatesResponse(
    val conversion_rates: Map<String, Double>,
    val base: String,
    val date: String
)


