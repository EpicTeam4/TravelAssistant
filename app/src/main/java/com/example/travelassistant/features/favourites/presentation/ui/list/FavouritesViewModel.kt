package com.example.travelassistant.features.favourites.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.commands.CommandsLiveData
import com.example.travelassistant.core.commands.GoToFragmentAndSendSafeArgs
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.core.parseError
import com.example.travelassistant.features.favourites.domain.usecase.SightsUseCase
import com.example.travelassistant.features.favourites.presentation.ui.SightsViewState
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
class FavouritesViewModel @Inject constructor(
    private val useCase: SightsUseCase
) : ViewModel() {

    var content = SightsViewState.Content()

    val commands = CommandsLiveData<ViewCommand>()

    private val dataContent = MutableLiveData<SightsViewState>()
    val dataState: LiveData<SightsViewState> get() = dataContent

    fun loadData() {
        viewModelScope.launch {
            when (val cities = useCase.getCities()) {
                is State.Success -> {
                    when (val sights = useCase.getFavouriteSights()) {
                        is State.Success -> {
                            handleData(cities = cities.data, sights = sights.data)
                        }
                        is State.Error -> {
                            handleError(sights.isNetworkError)
                        }
                    }
                }
                is State.Error -> {
                    handleError(cities.isNetworkError)
                }
            }
        }
    }

    private suspend fun handleError(networkError: Boolean) {
        withContext(Main) {
            dataContent.value = SightsViewState.Error(networkError.parseError())
        }
    }

    private suspend fun handleData(cities: List<City>, sights: List<Sights>) {
        withContext(Main) {
            dataContent.value = content.copy(cities = cities, sights = sights)
        }
    }

    fun openDetails(id: Int) {
        val action =
            FavouritesFragmentDirections.actionNavigationFavouritesToFavouritesDetailsFragment()
        action.placeId = id
        commands.onNext(GoToFragmentAndSendSafeArgs(action))
    }
}