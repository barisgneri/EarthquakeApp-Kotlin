package com.barisguneri.earthquakeapp.repository

import android.content.res.Resources
import com.barisguneri.earthquakeapp.model.Earthquake
import com.barisguneri.earthquakeapp.services.EarthquakeApi
import com.barisguneri.earthquakeapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class EarthquakeRepository @Inject constructor(private val api: EarthquakeApi){


    suspend fun getEarthquakeList() : Resource<Earthquake>{
        val response = try {
            api.getEarthquakeList()
        }catch (e: Exception){
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

}