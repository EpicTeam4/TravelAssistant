package com.example.travelassistant.features.luggage.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.commands.CommandsLiveData
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.parseError
import com.example.travelassistant.features.luggage.domain.usecase.GetLuggageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения списка вещей
 *
 * @author Marianne Sabanchieva
 */

@HiltViewModel
class LuggageViewModel @Inject constructor(private val useCase: GetLuggageUseCase) : ViewModel() {

    var luggageItem = PersonalItem()
    var content = LuggageViewState.Content()

    val commands = CommandsLiveData<ViewCommand>()

    private val dataContent = MutableLiveData<LuggageViewState>()
    val dataState: LiveData<LuggageViewState> get() = dataContent

    fun loadData() {
        viewModelScope.launch {
            when (val items = useCase.getAllItems()) {
                is State.Success -> handleData(items = items.data)
                is State.Error -> handleError(true)
            }
        }
    }

    private suspend fun handleData(items: List<PersonalItem>) {
        withContext(Dispatchers.Main) {
            dataContent.value = content.copy(items = items)
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Dispatchers.Main) {
            dataContent.value = LuggageViewState.Error(isNetworkError.parseError())
        }
    }

    fun addItem(item: PersonalItem) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                useCase.addItem(item)
            }
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                useCase.deleteItem(id)
            }
        }
    }

}