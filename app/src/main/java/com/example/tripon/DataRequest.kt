package com.example.tripon

import androidx.lifecycle.*
import com.example.tripon.model.TrainBwStaion.TrainBwStation
import com.example.tripon.model.emoji.EmojiItem
import com.example.tripon.model.flight.Aggregation
import com.example.tripon.model.flight.Flights
import com.example.tripon.model.hotel.Hotel
import com.example.tripon.model.hotel.Result
import com.example.tripon.model.liveTrain.LiveTrain
import com.example.tripon.model.stationCode.Data
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers


class StationViewModel(private val repository: StationRepository) : ViewModel() {

    private val _stationLiveCode = MutableLiveData<String>()
    val stationLiveCode: LiveData<String>
        get() = _stationLiveCode

    private val _trainBwStationLiveData = MutableLiveData<List<com.example.tripon.model.TrainBwStaion.Data>>()
    val trainBwStationLiveData: LiveData<List<com.example.tripon.model.TrainBwStaion.Data>>
        get() = _trainBwStationLiveData

    private val _hotel = MutableLiveData<List<Result>>()
    val hotelData: LiveData<List<Result>>
        get() = _hotel

    private val _flight = MutableLiveData<Aggregation>()
    val flightData: LiveData<Aggregation>
        get() = _flight

    private val _trainLiveData = MutableLiveData<com.example.tripon.model.liveTrain.Data>()
    val trainLiveData: LiveData<com.example.tripon.model.liveTrain.Data>
        get() = _trainLiveData

    private val _emoji = MutableLiveData<List<EmojiItem>>()
    val emoji: LiveData<List<EmojiItem>>
        get() = _emoji

    fun searchStation(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchStation(query)
                _stationLiveCode.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTrainBwStation(query: String,query1: String,query2: String){
        viewModelScope.launch {
            try {
                val result = repository.trainBwStation(query,query1,query2)
                _trainBwStationLiveData.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun liveTrain(query: String){
        viewModelScope.launch {
            try {
                val result = repository.liveTrainStatus(query)
                _trainLiveData.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun hotelList(query: String,query1: String,query2: String,query3: String){
        viewModelScope.launch {
            try {
                val result = repository.hotel(query, query1, query2, query3)
                _hotel.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun flights(query: String,query1: String){
        viewModelScope.launch {
            try {
                val result = repository.flight(query, query1)
                _flight.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun emoji(query: Int,query1: Int) {
        viewModelScope.launch {
            try {
                val result = repository.fetchEmoji(query, query1)
                _emoji.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clean up resources if needed
    }
}

class StationViewModelFactory(private val repository: StationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}