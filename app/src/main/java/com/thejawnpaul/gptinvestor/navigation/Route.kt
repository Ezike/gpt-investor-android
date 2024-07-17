package com.thejawnpaul.gptinvestor.navigation

sealed interface Route {
    val value: String

    data object Home : Route by Impl("home")
    data object NewsDetail : Route, WithArg {
        override val value: String get() = "news_detail/{$key}"
        override val key: String get() = "url_key"
    }
    data object CompanyDetail : Route, WithArg {
        override val value: String get() = "company_detail/{$key}"
        override val key: String get() = "ticker_key"
    }

    interface WithArg {
        val key: String
    }
}

private class Impl(override val value: String) : Route

val Route.argKey: String?
    get() = (this as? Route.WithArg)?.key
