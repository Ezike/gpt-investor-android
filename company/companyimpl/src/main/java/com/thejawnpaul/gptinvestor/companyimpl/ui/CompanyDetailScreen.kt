package com.thejawnpaul.gptinvestor.companyimpl.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thejawnpaul.gptinvestor.companyimpl.CompanyViewModel
import com.thejawnpaul.gptinvestor.companyimpl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailScreen(
  modifier: Modifier = Modifier,
  viewModel: CompanyViewModel,
  onNavigationBtnClick: () -> Unit,
  onNewsClick: (String) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
  Scaffold(
    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MediumTopAppBar(
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
          ),
        title = {
          Text(text = viewModel.companyName, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
          IconButton(
            onClick = {
              onNavigationBtnClick()
              viewModel.resetSimilarCompanies()
            }
          ) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = stringResource(id = R.string.back),
            )
          }
        },
        actions = {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.Outlined.Star,
              contentDescription = stringResource(id = R.string.star),
            )
          }
        },
        scrollBehavior = scrollBehavior,
      )
    },
  ) { innerPadding ->
    val state = viewModel.selectedTab.collectAsState()
    val titles = listOf("Data", "News", "AI")
    Box(
      modifier =
        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
          .padding(innerPadding)
          .fillMaxSize()
    ) {
      // TODO(): fix scroll issue
      Column(modifier = Modifier.padding(horizontal = 0.dp)) {
        PrimaryTabRow(selectedTabIndex = state.value) {
          titles.forEachIndexed { index, title ->
            Tab(
              selected = state.value == index,
              onClick = { viewModel.selectTab(index) },
              text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            )
          }
        }
        AnimatedContent(
          modifier = Modifier.verticalScroll(rememberScrollState()),
          targetState = state.value,
        ) { targetState ->
          when (targetState) {
            0 -> CompanyDataScreen(viewModel = viewModel)
            1 -> CompanyNewsScreen(viewModel = viewModel, onNewsClick = onNewsClick)
            2 -> AIInvestorScreen(viewModel = viewModel)
          }
        }
      }
    }
  }
}
