package com.thejawnpaul.gptinvestor.features.company.model

data class Company(
    val ticker: String,
    val name: String,
    val summary: String,
    val logo: String
)

data class CompanyDetails(
    val company: Company,
    val sector: String,
    val industry: String,
)