package com.barisguneri.earthquakeapp.presentation.earthquake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EarthquakeViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EarthquakeScreenState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(EarthquakeScreenEvent.LoadEarthquakes)
    }

    fun onEvent(event: EarthquakeScreenEvent) {
        when (event) {
            is EarthquakeScreenEvent.LoadEarthquakes -> {
                loadEarthquakes()
            }
            is EarthquakeScreenEvent.Retry -> {
                loadEarthquakes()
            }
        }
    }

    private fun loadEarthquakes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val earthquakesFlow = getEarthquakesUseCase()
                    .cachedIn(viewModelScope)

                // 2. Başarılı bir şekilde PagingData akışını oluşturduysak,
                // EKRAN SEVİYESİNDEKİ yüklemeyi bitir ve akışı state'e ata.
                // Bu andan sonra PagingData'nın kendi loadState'i devreye girer.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pagingDataFlow = earthquakesFlow
                    )
                }
            } catch (e: Exception) {
                // 3. Eğer UseCase veya PagingData akışını oluşturma sırasında
                // beklenmedik bir hata olursa, bunu EKRAN SEVİYESİNDE bir hata olarak yakala.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = ErrorType.Unknown(e) // Veya daha spesifik bir hata
                    )
                }
            }
        }
    }
}