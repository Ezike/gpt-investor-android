package com.thejawnpaul.gptinvestor.features.home.model

data class SimilarCompanies(
    val codeText: String?,
    val companies: List<String>
) {
    val cleaned = companies.map { it.removeSurrounding("\"") }
}
