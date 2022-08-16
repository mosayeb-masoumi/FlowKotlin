package com.example.flowkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        CoroutineScope(Main).launch {
            mainViewModel.countDownFlow.collect {
                txt.text = it.toString()
            }
        }





       /*********************** stateFlow ************************/
        //statFlow prevent loosing data when screen orientaion happen
        CoroutineScope(Main).launch {
            mainViewModel.stateFlow.collect {
                txt_stateflow.text = it.toString()
            }
        }

        btn_stateflow.setOnClickListener {
            mainViewModel.incrementCounter()
        }



    }
}