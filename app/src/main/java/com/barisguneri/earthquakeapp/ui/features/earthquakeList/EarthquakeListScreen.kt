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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.common.PagingException
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.core.presentation.ErrorView
import com.barisguneri.earthquakeapp.core.presentation.MapView
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.component.EarthquakeItem
import org.osmdroid.util.GeoPoint

@Composable
fun EarthquakeListScreen(
    onNavigateToEarthquakeDetail: (earthquakeId: String) -> Unit,
    viewModel: EarthquakeViewModel = hiltViewModel()
){
    // ViewModel'deki StateFlow'u, lifecycle'a duyarlı bir şekilde dinliyoruz.
    // `collectAsStateWithLifecycle`, ekran arka plana gittiğinde gereksiz yere
    // veri toplamayı durdurur. Bu, pil ömrü ve performans için kritiktir.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // State içindeki PagingData Flow'unu, LazyColumn'un anlayacağı
    // LazyPagingItems nesnesine dönüştürüyoruz. Asıl veri yükleme işini bu tetikler.
    val pagingItems = state.pagingDataFlow.collectAsLazyPagingItems()

    EarthquakeContent(
        pagingItems = pagingItems,
        onEvent = viewModel::onEvent,
        onEarthquakeClick = onNavigateToEarthquakeDetail
    )
}

@Composable
private fun EarthquakeContent(
    pagingItems: LazyPagingItems<EarthquakeInfo>,
    onEvent: (EarthquakeScreenEvent) -> Unit,
    onEarthquakeClick: (earthquakeId: String) -> Unit
) {
    val allEarthquakes = remember { mutableStateListOf<EarthquakeInfo>() }
    Box(modifier = Modifier.fillMaxSize()) {
        //TopMapContent(earthquakeInfo = allEarthquakes)
        // `loadState.refresh`, listenin ilk kez yüklendiği veya elle yenilendiği durumu temsil eder.
        // Bu, bizim `State` sınıfımızdaki `isLoading`/`error`'dan daha spesifiktir,
        // çünkü doğrudan Paging verisiyle ilgilidir.
        when (val refreshState = pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                // İlk Yükleme Durumu
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is LoadState.Error -> {
                // İlk Yükleme Hatası Durumu
                // PagingSource'ta fırlattığımız özel PagingException'ı burada yakalıyoruz.
                val error = refreshState.error as? PagingException
                ErrorView(
                    errorType = error?.errorType ?: ErrorType.Unknown(refreshState.error),
                    onRetry = { onEvent(EarthquakeScreenEvent.Retry) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else ->
                // Yükleme başarılı oldu veya henüz başlamadı. Listeyi göster.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = { index -> pagingItems[index]?.id ?: index } // Performans için anahtar
                    ) { index ->
                        val earthquake = pagingItems[index]
                        earthquake?.let {
                            allEarthquakes.add(it)
                            EarthquakeItem(earthquake = it, onClick = { onEarthquakeClick(it.id) })
                        }
                    }

                    // Listenin sonuna gelindiğinde yeni sayfa yükleniyorsa...
                    when (pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimensionResource(R.dimen.size16dp))) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                Text(stringResource(R.string.no_more_load_please_try_again))
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

@Composable
fun TopMapContent(modifier: Modifier = Modifier, earthquakeInfo: SnapshotStateList<EarthquakeInfo>) {
    Card(modifier = modifier.wrapContentSize(), shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)) {
        MapView(modifier = modifier.fillMaxSize(), markersData = earthquakeInfo.map { detail ->
            MapMarkerData(
                position = GeoPoint(
                    detail.location.lat,
                    detail.location.long
                ),
                title = detail.title,
                subDescription = "İl: ${detail.title}}\nBüyüklük: ${detail.magnitude}",
                magnitude = detail.magnitude
            )
        }
        )
    }
}

private fun magColor(mag: Double) : Color{
    return if (mag > 4 && mag < 5.0){
        Color.Yellow
    }else if(mag > 5.0){
        Color.Red
    }else{
        Color.Black
    }
}
