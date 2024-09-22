package com.thejawnpaul.gptinvestor.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModelStoreOwner

interface Page {
  val route: Route

  @Composable fun Content(pageContext: PageContext)
}

@Stable data class PageContext(val navArgs: String?, val navigator: Navigator)

/**
 * Returns a [ViewModelStoreOwner] for the given [route]. Can be used to share the same ViewModel
 * instance. Eg:
 * ```kotlin
 *    val sharedViewModel =
 *             hiltViewModel<ViewModel>(pageContext.getViewModelStoreOwner(Route))
 */
fun PageContext.getViewModelStoreOwner(route: Route): ViewModelStoreOwner =
  navigator.getBackstackEntry(route)
