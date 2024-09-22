package com.thejawnpaul.gptinvestor.features.company.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class CompanyEntity(
  @PrimaryKey val ticker: String,
  val summary: String,
  val industry: String,
  val industryKey: String,
  val sector: String,
  val sectorKey: String,
  val country: String,
  val name: String,
  val logoUrl: String,
  val website: String,
  val date: String,
)
