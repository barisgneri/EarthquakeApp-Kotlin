package com.barisguneri.earthquakeapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    var earthquakeList = mutableStateOf<List<Result>>(listOf())
    var errorMsg = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    private var _earthquake: MutableState<List<Result>>? = mutableStateOf<List<Result>>(emptyList())
    val earthquake: State<List<Result>>? = _earthquake

    init {
        loadEarthquakeList()
    }

    private fun loadEarthquakeList() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getEarthquakeList()
            when (result) {
                is Resource.Success -> {
                    earthquakeList.value = listOf()
                    val allEarthquake = result.data?.result as List<Result>
                    earthquakeList.value += allEarthquake
                }

                is Resource.Error -> {
                }

                else -> {}
            }
        }
    }

    fun setEarthquake(_data: Result) {
        _earthquake?.value = listOf(_data)
    }
}