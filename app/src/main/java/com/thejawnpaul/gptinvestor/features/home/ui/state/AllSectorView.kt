package com.thejawnpaul.gptinvestor.features.home.ui.state

import com.thejawnpaul.gptinvestor.features.company.model.SectorInput

data class AllSectorView(
    val sectors: List<SectorInput> = emptyList(),
    val selected: SectorInput = SectorInput.AllSector
)
