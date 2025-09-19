package com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.CollectWithLifecycle
import com.barisguneri.earthquakeapp.core.data.ErrorType
import com.barisguneri.earthquakeapp.core.data.PagingException
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.ui.components.ErrorView
import com.barisguneri.earthquakeapp.ui.components.LoadingView
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui.component.EarthquakeItem
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui.component.ListScreenTopAppBar
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation.ListNavActions
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import kotlinx.coroutines.flow.Flow
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiAction
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiState
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiEffect

@Composable
fun EarthquakeListScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: ListNavActions,
    pagingItems: LazyPagingItems<EarthquakeInfo>
) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {}
            is UiEffect.NavigateToDetail -> navActions.navigateToDetail(effect.earthquakeId)
        }
    }

    when {
        uiState.isLoading -> LoadingView(modifier = Modifier.fillMaxSize())
        uiState.error != null -> ErrorView(
            errorType = uiState.error,
            onRetry = { onAction(UiAction.Retry) },
            modifier = Modifier.fillMaxSize()
        )

        else -> {
            EarthquakeContent(
                pagingItems = pagingItems,
                onAction = onAction,
                uiState = uiState
            )
        }
    }


}

@Composable
private fun EarthquakeContent(
    pagingItems: LazyPagingItems<EarthquakeInfo>,
    onAction: (UiAction) -> Unit,
    uiState: UiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (val refreshState = pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is LoadState.Error -> {
                val error = refreshState.error as? PagingException
                ErrorView(
                    errorType = error?.errorType ?: ErrorType.Unknown(
                        apiCode = error.hashCode(),
                        message = refreshState.error.toString()
                    ),
                    onRetry = { onAction(UiAction.Retry) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            else -> {
                ListScreenTopAppBar(onAction = onAction, filterState = uiState.filterState)
                SuccessEarthquakeDataContent(earthquakeList = pagingItems, onAction = onAction)
            }
        }
    }
}

@Composable
fun SuccessEarthquakeDataContent(
    earthquakeList: LazyPagingItems<EarthquakeInfo>,
    onAction: (UiAction) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(
            count = earthquakeList.itemCount,
            key = { index -> earthquakeList.peek(index)?.id + index }
        ) { index ->
            val earthquake = earthquakeList[index]
            if (earthquake != null) {
                EarthquakeItem(
                    earthquake = earthquake,
                    onClick = { onAction(UiAction.OnEarthquakeClick(earthquake.id)) })
            }else{
                Text(text = "Uygun Sonuç Bulunamadı")
            }
        }

        when (val appendState = earthquakeList.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingView(modifier = Modifier.fillMaxWidth())
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
                    Button(onClick = { earthquakeList.retry() }) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }

            else -> {

            }
        }
    }
}
