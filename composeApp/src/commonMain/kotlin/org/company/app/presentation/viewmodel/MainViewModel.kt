package org.company.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.company.app.domain.model.categories.NewsCategoriesItem
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.model.news.NewsList
import org.company.app.domain.repository.Repository
import org.company.app.domain.usecase.ResultState

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _latestListing = MutableStateFlow<ResultState<LatestListing>>(ResultState.LOADING)
    var latestListing: StateFlow<ResultState<LatestListing>> = _latestListing.asStateFlow()

    private val _allNews = MutableStateFlow<ResultState<NewsList>>(ResultState.LOADING)
    var allNews: StateFlow<ResultState<NewsList>> = _allNews.asStateFlow()

    private val _newsCategories = MutableStateFlow<ResultState<List<NewsCategoriesItem>>>(ResultState.LOADING)
    var newsCategories: StateFlow<ResultState<List<NewsCategoriesItem>>> = _newsCategories.asStateFlow()

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

    fun getAllNews(){
        viewModelScope.launch {
            _allNews.value = ResultState.LOADING
            try {
                val response = repository.getAllNews()
                _allNews.value = ResultState.SUCCESS(response)
            }catch (e:Exception){
                _allNews.value = ResultState.ERROR(e.message.toString())
            }
        }
    }
    fun getNewsCategories(){
        viewModelScope.launch {
            _newsCategories.value = ResultState.LOADING
            try {
                val response = repository.getNewsCategories()
                _newsCategories.value = ResultState.SUCCESS(response)
            }catch (e:Exception){
                _newsCategories.value = ResultState.ERROR(e.message.toString())
            }
        }
    }
}