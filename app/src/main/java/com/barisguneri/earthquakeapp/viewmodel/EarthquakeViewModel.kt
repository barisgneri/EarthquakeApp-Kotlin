package com.barisguneri.earthquakeapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisguneri.earthquakeapp.model.Earthquake
import com.barisguneri.earthquakeapp.model.Result
import com.barisguneri.earthquakeapp.repository.EarthquakeRepository
import com.barisguneri.earthquakeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EarthquakeViewModel @Inject constructor(val repository: EarthquakeRepository) :
    ViewModel() {
        var earthquakeList = mutableStateOf<List<Earthquake>>(listOf())
        var errorMsg = mutableStateOf("")
        var isLoading = mutableStateOf(false)

    init {
        loadEarthquakeList()
    }

    private fun loadEarthquakeList(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getEarthquakeList()
            when(result){
                is Resource.Success -> {
                    val allEarthquake = result.data?.result?.mapIndexed{ index, earthquake ->

                    } as List<Earthquake>
                    earthquakeList.value += allEarthquake

                }
                is Resource.Error -> {
                }
                else -> {}
            }
        }
    }

                    val cryptoItems = result.data!!.mapIndexed { index, item ->
                        CryptoListItem(item.currency,item.price)
                    } as List<CryptoListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    errorMessage.value = ""
                }
            }
        }
    }
}