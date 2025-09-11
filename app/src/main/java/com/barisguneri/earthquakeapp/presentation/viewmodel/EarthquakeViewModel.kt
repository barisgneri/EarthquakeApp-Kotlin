package com.barisguneri.earthquakeapp.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisguneri.earthquakeapp.data.api.model.ResultDTO
import com.barisguneri.earthquakeapp.common.Resource
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EarthquakeViewModel @Inject constructor(val repository: EarthquakeRepository) :
    ViewModel() {
    var earthquakeList = mutableStateOf<List<ResultDTO>>(listOf())
    var errorMsg = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    private var _earthquake: MutableState<List<ResultDTO>>? = mutableStateOf<List<ResultDTO>>(emptyList())
    val earthquake: State<List<ResultDTO>>? = _earthquake

    init {
        loadEarthquakeList()
    }

    private fun loadEarthquakeList() {
        viewModelScope.launch {
            isLoading.value = true

        }
    }

    fun setEarthquake(_data: ResultDTO) {
        _earthquake?.value = listOf(_data)
    }
}