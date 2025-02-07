package com.thejawnpaul.gptinvestor.homeimpl.model

sealed class SectorInput {
  data object AllSector : SectorInput()

  data class CustomSector(val sectorName: String, val sectorKey: String) : SectorInput()
}
