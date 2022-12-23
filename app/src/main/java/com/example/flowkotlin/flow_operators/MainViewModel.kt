package com.example.flowkotlin.flow_operators


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class MainViewModel @Inject constructor() : ViewModel() {
class MainViewModel : ViewModel() {


    var countDownFlow = flow<Int> {

        val startingValue = 10
        var currentValue = startingValue

        emit(currentValue)
        while (currentValue > 0) {

            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }


    init {
        collectFlow()
    }

//    for collect in viewModel
//    private fun collectFlow(){
//        viewModelScope.launch {
//            countDownFlow.collect{
//                Log.i("Time", "Time is ${it.toString()}")
//            }
//        }
//    }

    //collectLatest canceled the old one
//    private fun collectFlow(){
//        viewModelScope.launch {
//            countDownFlow.collectLatest{
//                delay(1500L)
//                Log.i("Time", "Time is ${it.toString()}")
//            }
//        }
//    }


    /**** operator 1 & 2 ***/
    /************************** filter and map operators *************************/
    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow
                .filter { time ->
                    time % 2 == 0   // just collect even digits
                }
                .map { time ->

                    time*2       // do an operation on even digits
                }

                .onEach { time ->

                    Log.i("Time onEach", "Time is $time")  //.onEach is the same as .collect
                }

                .collect { time ->
                    Log.i("Time collect", "Time is $time")
                }
        }
    }


    /************************** these 2 below are the same *************************/

//    private fun collectFlow() {
//
//        countDownFlow.onEach {
//             println(it)
//            Log.i("Time onEach", "Time is $it")
//        }.launchIn(viewModelScope)
//
//       /********* == **********/
//
//        viewModelScope.launch {
//            countDownFlow.collect{
//                println(it)
//                Log.i("Time collect", "Time is $it")
//            }
//        }
//
//    }


    /**** operator 3 ***/
    /************************** count operator *************************/
    /************************** it counting the number of emission
     * for example if we start countDown from 10 and filter even numbers
     * the count of emission will be 6  (10 , 8, 6 ,4 ,2 ,0)  , 6 times count will trig *************************/

//    private fun collectFlow() {
//        viewModelScope.launch {
//            val count = countDownFlow
//
//                .count {
//                    it % 2 == 0  // just count the even digits
//                }
//
//            println("The count is $count")
//        }
//    }


    /**** operator 4 ***/
    /********  reduce operator ***************/
    /** this operator add all digits (10+9+8+7+6+5+4+3+2+1+0) == 55 *********/

//    private fun collectFlow() {
//        viewModelScope.launch {
//            val reduceResult = countDownFlow
//
//                .reduce { accumulator, value ->
//
//                    accumulator + value
//                }
//
//            println("The add is $reduceResult")
//        }
//    }


    /**** operator 5 ***/
    /********  fold operator ***************/
    /** this operator is the same as reduce operator but in this operator we can set an initial value *********/
    /** (initial value) 100 + (10+9+8+7+6+5+4+3+2+1+0) == 155 ***********/
//
//    private fun collectFlow() {
//        viewModelScope.launch {
//            val reduceResult = countDownFlow
//
//                .fold(100) { accumulator, value ->
//                    accumulator + value
//                }
//
//            println("The add is $reduceResult")
//        }
//    }



    /**** operator 6 ***/
    /****************************************************** flatMapConcat **********************************************************/
    // [[1,2],[1,2,3]] ====>  turn to one list [1,2,1,2,3]

//    private fun collectFlow() {
//
//        val flow1 = flow {
//             emit(1)
//             delay(500L)
//             emit(2)
//        }
//
//        viewModelScope.launch {
//
//            flow1.flatMapConcat { value ->
//
//                flow {
//                    emit(value + 1)
//                    delay(500L)
//                    emit(value + 2)
//                }
//
//            }.collect{ value ->
//                println("The value is $value")
//
//                //result
//                // The value is 2   ( 1+1  first emit of flow1+first emit of flow)
//                // The value is 3   ( 1+2  first emit of flow1+second emit of flow)
//                // The value is 3   ( 2+1  second emit of flow1+first emit of flow)
//                // The value is 4   ( 2+2  second emit of flow1+second emit of flow)
//            }
//
//
//        }
//    }


    /******* practical example of flatMapConcat *******/
//    private fun collectFlow() {
//
//        val flow1 = (1..5).asFlow()
//
//        viewModelScope.launch {
//
//            flow1.flatMapConcat { id ->
//
//                //  getUserById(id)
//
//            }.collect { value ->
//                println("The value is $value")
//            }
//        }
//    }


    /******************************************** example 1 ***************************************/
    /** in this example first emit(appetizer) must be collect after that the second emit(Main Dish)
     *will emit , then emits are in row   the second emit(Main Dish) must wait the first emit(Appetizer) to be collected then allowed to emit*/
//    private fun collectFlow() {
//
//        val flow = flow {
//            delay(250L)
//            emit("Appetizer")
//            delay(1000L)
//            emit("Main Dish")
//            delay(100L)
//            emit("Dessert")
//        }
//
//        viewModelScope.launch {
//            flow.onEach {
//
//                println("FLOW: $it is delivered")
//
//            }.collect {
//                println("FLOW: now eating $it")
//                delay(1500L)
//                println("FLOW: finish eating $it")
//            }
//
//
//        }
//    }


    /**** operator 7 ***/
    /******************************************** example 2 ***************************************/
    /**  in this example we used buffer operator , buffer don't hold the emit to be collected then emit the second one
     * all emits will be emitted without any notice of former emit collected or no   speed is more than former example*/
//    private fun collectFlow() {
//
//        val flow = flow {
//            delay(250L)
//            emit("Appetizer")
//            delay(1000L)
//            emit("Main Dish")
//            delay(100L)
//            emit("Dessert")
//        }
//        viewModelScope.launch {
//            flow.onEach {
//                println("FLOW: $it is delivered")
//            }.buffer()
//                .collect {
//                    println("FLOW: now eating $it")
//                    delay(1500L)
//                    println("FLOW: finish eating $it")
//                }
//
//        }
//    }





    /**** collect latest ***/
    /******************************************** example 3 ***************************************/
    /**  in this example just the latest emit will be proceed  for example skip finish eating Appetizer*/
//    private fun collectFlow() {
//
//        val flow = flow {
//            delay(250L)
//            emit("Appetizer")
//            delay(1000L)
//            emit("Main Dish")
//            delay(100L)
//            emit("Dessert")
//        }
//        viewModelScope.launch {
//            flow.onEach {
//                println("FLOW: $it is delivered")
//            }
//                .collectLatest {
//                    println("FLOW: now eating $it")
//                    delay(1500L)
//                    println("FLOW: finish eating $it")
//                }
//
//        }
//    }




    /******************************************** state flow 3 ***************************************/

    private val _stateFlow = MutableStateFlow(0)   // 0 is initial value
    val stateFlow = _stateFlow.asStateFlow()


    fun incrementCounter(){
        _stateFlow.value += 1
    }
}