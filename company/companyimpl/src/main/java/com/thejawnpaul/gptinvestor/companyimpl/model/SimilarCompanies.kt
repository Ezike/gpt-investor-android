package com.thejawnpaul.gptinvestor.companyimpl.model

data class SimilarCompanies(val codeText: String?, val companies: List<String>) {
  val cleaned = companies.map { it.removeSurrounding("\"") }
}
