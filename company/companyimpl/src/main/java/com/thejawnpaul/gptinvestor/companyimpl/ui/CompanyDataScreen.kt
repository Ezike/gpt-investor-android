package com.thejawnpaul.gptinvestor.companyimpl.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.halilibo.richtext.ui.Table
import com.halilibo.richtext.ui.material3.RichText
import com.thejawnpaul.gptinvestor.companyimpl.CompanyViewModel
import com.thejawnpaul.gptinvestor.companyimpl.R
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsInfo
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.SingleCompanyView

@Composable
fun CompanyDataScreen(modifier: Modifier = Modifier, viewModel: CompanyViewModel) {
  val company = viewModel.selectedCompany.collectAsStateWithLifecycle(SingleCompanyView())
  val financials = viewModel.companyFinancials.collectAsStateWithLifecycle()
  Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
    ElevatedCard(modifier = Modifier.padding(bottom = 16.dp)) {
      CardContent(
        title = stringResource(id = R.string.about),
        content = company.value.company?.summary ?: "",
      )
    }
    ElevatedCard {
      CardNumbers(
        title = stringResource(id = R.string.latest_financials),
        financials = financials.value.info,
      )
    }
  }
}

@Composable
private fun CardContent(title: String, content: String) {
  var expanded by rememberSaveable { mutableStateOf(false) }
  Row(
    modifier =
      Modifier.padding(16.dp)
        .animateContentSize(
          animationSpec =
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
  ) {
    Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 8.dp),
      )
      if (expanded) {
        Text(text = content)
      }
    }

    IconButton(onClick = { expanded = !expanded }) {
      Icon(
        imageVector =
          if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
        contentDescription =
          if (expanded) {
            stringResource(R.string.show_less)
          } else {
            stringResource(R.string.show_more)
          },
      )
    }
  }
}

@Composable
private fun CardNumbers(title: String, financials: CompanyFinancialsInfo?) {
  var expanded by rememberSaveable { mutableStateOf(false) }
  Row(
    modifier =
      Modifier.padding(16.dp)
        .animateContentSize(
          animationSpec =
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
  ) {
    Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
      Box(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleLarge,
          modifier = Modifier.padding(top = 8.dp),
        )
        IconButton(
          onClick = { expanded = !expanded },
          modifier = Modifier.align(Alignment.TopEnd),
        ) {
          Icon(
            imageVector =
              if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription =
              if (expanded) {
                stringResource(R.string.show_less)
              } else {
                stringResource(R.string.show_more)
              },
          )
        }
      }
      if (expanded) {
        if (financials != null) {
          RichText {
            Table(modifier = Modifier.fillMaxWidth()) {
              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.open),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.open) }
              }

              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.high),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.high) }
              }

              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.low),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.low) }
              }

              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.close),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.close) }
              }
              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.volume),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.volume) }
              }
              row {
                cell {
                  Text(
                    text = stringResource(id = R.string.market_cap),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  )
                }
                cell { Text(financials.marketCap) }
              }
            }
          }
        }
      }
    }
  }
}
