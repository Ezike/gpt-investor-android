package com.thejawnpaul.gptinvestor.homeimpl.ui.state

import com.thejawnpaul.gptinvestor.homeimpl.model.SectorInput

data class AllSectorView(
  val sectors: List<SectorInput> = emptyList(),
  val selected: SectorInput = SectorInput.AllSector,
)
