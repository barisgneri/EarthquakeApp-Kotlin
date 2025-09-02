package com.barisguneri.earthquakeapp.ui.features.earthquakeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.CollectWithLifecycle
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.core.common.PagingException
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.core.presentation.ErrorView
import com.barisguneri.earthquakeapp.core.presentation.LoadingView
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.component.EarthquakeItem
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiState
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiEffect
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation.ListNavActions
import kotlinx.coroutines.flow.Flow

@Composable
fun EarthquakeListScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: ListNavActions

) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {}
            is UiEffect.NavigateToDetail -> navActions.navigateToDetail(effect.earthquakeId)
        }
    }

    when{
        uiState.isLoading -> LoadingView()
        uiState.error != null -> ErrorView(errorType = uiState.error, onRetry = { onAction(UiAction.Retry) })
        else -> {
            val pagingItems = uiState.pagingDataFlow.collectAsLazyPagingItems()
            EarthquakeContent(
                pagingItems = pagingItems,
                onAction = onAction,
            )
        }
    }



}

@Composable
private fun EarthquakeContent(
    pagingItems: LazyPagingItems<EarthquakeInfo>,
    onAction: (UiAction) -> Unit
) {
    val allEarthquakes = remember { mutableStateListOf<EarthquakeInfo>() }
    Box(modifier = Modifier.fillMaxSize()) {
        // loadState.refresh, listenin ilk kez yüklendiği veya elle yenilendiği durumu temsil eder.
        when (val refreshState = pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is LoadState.Error -> {
                val error = refreshState.error as? PagingException
                ErrorView(
                    errorType = error?.errorType ?: ErrorType.Unknown(
                        apiCode = error.hashCode(),
                        message = refreshState.error.toString()
                    ),
                    onRetry = { onAction(UiAction.Retry) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = { index -> (pagingItems[index]?.id + index) } // Performans için anahtar apiden aynı id döndüğü için index ekledim
                    ) { index ->
                        val earthquake = pagingItems[index]
                        earthquake?.let {
                            allEarthquakes.add(it)
                            EarthquakeItem(earthquake = it, onClick = { onAction(UiAction.OnEarthquakeClick(it.id)) })
                        }
                    }

                    // Listenin sonuna gelindiğinde yeni sayfa yükleniyorsa...
                    when (val appendState = pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = dimensionResource(R.dimen.size16dp))
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }

                        is LoadState.Error -> {
                            val error = appendState.error as PagingException
                            val errorTitle = if (error.errorType == ErrorType.TooManyRequests) {
                                R.string.too_many_request
                            } else {
                                R.string.no_more_load_please_try_again
                            }
                            item {
                                Text(stringResource(errorTitle))
                                Button(onClick = { pagingItems.retry() }) {
                                    Text(stringResource(R.string.try_again))
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }
}
