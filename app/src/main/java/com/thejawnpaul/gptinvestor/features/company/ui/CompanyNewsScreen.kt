package com.thejawnpaul.gptinvestor.features.company.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejawnpaul.gptinvestor.features.company.CompanyViewModel

@Composable
fun CompanyNewsScreen(
    modifier: Modifier,
    onNewsClick: (String) -> Unit,
    viewModel: CompanyViewModel
) {
    val financials by viewModel.companyFinancials.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        financials.info?.let { info ->
            info.news.forEach { news ->
                SingleNewsItem(
                    modifier = Modifier,
                    newsInfo = news,
                    onClick = onNewsClick
                )
            }
        }
    }
}
