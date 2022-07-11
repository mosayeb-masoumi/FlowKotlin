package com.example.flowkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.flowkotlin.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn_get_list.setOnClickListener {


            mainViewModel.getEmployee()
            val getItemLiveData = mainViewModel.getListLiveData
            getItemLiveData.observe(this) {

                if(it !=null){
                    if (it.status == Resource.Status.SUCCESS) {

                        val result = it.data?.get(0)?.title
                        txt.text = result
                        getItemLiveData.removeObservers(this)
                        mainViewModel.onUserClear()  // to prevent looping

                    } else if (it.status == Resource.Status.ERROR) {
                        txt.text = it.message

                    } else if (it.status == Resource.Status.LOADING) {
                        txt.text = "loading..."
                    }
                }

            }
        }
    }
}