package com.example.flowkotlin.flows_comparison

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComparisonViewModel : ViewModel() {

    private val _liveData = MutableLiveData("Hello World")
    val liveData :LiveData<String> = _liveData


    // a good replacement for liveData keeps the latest value and here we can have operators like filter , map and so on
    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow = _stateFlow.asStateFlow()


    // good for sending event to ui
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()



    fun triggerLiveData(){
        _liveData.value = "LiveData"
    }

    fun triggerStateFlow(){
        _stateFlow.value = "StateFlow"
    }


    // good for logic and emit multiple value to ui in a period of time
    fun triggerFlow():Flow<Int> {
         return flow {
             repeat(60){
                 emit( it + 1)
                 delay(1000L)
             }
         }
    }

    fun triggerSharedFlow(){

        viewModelScope.launch {
            _sharedFlow.emit("sharedFlow")
        }
    }
}