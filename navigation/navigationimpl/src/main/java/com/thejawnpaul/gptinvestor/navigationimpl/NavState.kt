package com.thejawnpaul.gptinvestor.navigationimpl

import kotlinx.coroutines.flow.Flow

interface NavState {
  val navEvents: Flow<NavEvent>
}

sealed class NavEvent {
  class ToRoute(val route: String) : NavEvent()

  object Up : NavEvent()
}
