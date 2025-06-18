package com.barisguneri.earthquakeapp.presentation.earthquake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.common.PagingException
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo

@Composable
fun ListScreen(
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
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EarthquakeContent(
    pagingItems: LazyPagingItems<EarthquakeInfo>,
    onEvent: (EarthquakeScreenEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

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
            else -> {
                // Yükleme başarılı oldu veya henüz başlamadı. Listeyi göster.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = { index -> pagingItems[index]?.id ?: index } // Performans için anahtar
                    ) { index ->
                        val earthquake = pagingItems[index]
                        earthquake?.let {
                            EarthquakeItem(earthquake = it)
                        }
                    }

                    // Listenin sonuna gelindiğinde yeni sayfa yükleniyorsa...
                    when (pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        is LoadState.Error -> {
                            // Opsiyonel: Listenin sonunda hata olursa gösterilecek item
                            item {
                                Text("Daha fazla yüklenemedi. Lütfen tekrar deneyin.")
                                Button(onClick = { pagingItems.retry() }) {
                                    Text("Tekrar Dene")
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun EarthquakeItem(earthquake: EarthquakeInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = earthquake.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "${earthquake.date} - ${earthquake.dateTime}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "${earthquake.depthInfo} - ${earthquake.magnitude} - ${earthquake.location.lat} - ${earthquake.location.long}" , style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "%.1f".format(earthquake.magnitude),
                style = MaterialTheme.typography.headlineSmall,
                color = when {
                    earthquake.magnitude >= 5.0 -> MaterialTheme.colorScheme.error
                    earthquake.magnitude >= 4.0 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
fun ErrorView(
    errorType: ErrorType,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val message = when (errorType) {
        is ErrorType.NoInternetConnection -> "İnternet bağlantınızı kontrol edin."
        is ErrorType.HttpError -> "Sunucuya ulaşırken bir sorun oluştu. (Kod: ${errorType.code})"
        else -> "Beklenmedik bir hata oluştu. Lütfen tekrar deneyin."
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tekrar Dene")
        }
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
