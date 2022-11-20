package com.example.travelassistant.features.favourites.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.favourites.domain.usecase.SightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения информации об избранных достопримечательностях
 *
 * @author Marianne Sabanchieva
 */

@HiltViewModel
class SightsViewModel @Inject constructor(
    private val useCase: SightsUseCase
) : ViewModel() {

    private val dataContent = MutableLiveData<PlaceDomain>()
    val dataState: LiveData<PlaceDomain> get() = dataContent

    fun loadSights(id: String) {
        viewModelScope.launch {
            withContext(Main) {
                dataContent.value = useCase.getSightsById(id)
            }
        }
    }

    fun deleteSights(id: PlaceDomain) {
        viewModelScope.launch {
            withContext(Main) {
                useCase.deleteSightsFromFavourite(id)
            }
        }
    }
}