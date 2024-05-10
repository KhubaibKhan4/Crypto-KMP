package org.company.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.repository.Repository
import org.company.app.domain.usecase.ResultState

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _latestListing = MutableStateFlow<ResultState<LatestListing>>(ResultState.LOADING)
    var latestListing: StateFlow<ResultState<LatestListing>> = _latestListing.asStateFlow()

    fun getLatestListing() {
        viewModelScope.launch {
            _latestListing.value = ResultState.LOADING
            try {
                val response = repository.getLatestListing()
                _latestListing.value = ResultState.SUCCESS(response)
            } catch (e: Exception) {
                _latestListing.value = ResultState.ERROR(e.message.toString())
            }
        }
    }

}