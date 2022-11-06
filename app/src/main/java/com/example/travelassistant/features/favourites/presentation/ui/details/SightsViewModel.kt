package com.example.travelassistant.features.favourites.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.domain.entity.Sights
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

    private val dataContent = MutableLiveData<Sights>()
    val dataState: LiveData<Sights> get() = dataContent

    fun loadSights(id: Int) {
        viewModelScope.launch {
            withContext(Main) {
                dataContent.value = useCase.getSightsById(id)
            }
        }
    }
}