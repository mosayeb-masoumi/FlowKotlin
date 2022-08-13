package com.example.flowkotlin


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel(){



    var countDownFlow = flow<Int> {

        val startingValue = 10
        var currentValue = startingValue

        emit(currentValue)
        while (currentValue >0){

            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }




    init {
        collectFlow()
    }

    //for collect in viewModel
    private fun collectFlow(){
        viewModelScope.launch {
            countDownFlow.collect{
                Log.i("Time", "Time is ${it.toString()}")
            }
        }
    }

     //collectLatest canceled the old one
//    private fun collectFlow(){
//        viewModelScope.launch {
//            countDownFlow.collectLatest{
//                delay(1500L)
//                Log.i("Time", "Time is ${it.toString()}")
//            }
//        }
//    }

}