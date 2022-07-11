package com.example.flowkotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowkotlin.common.Resource
import com.example.flowkotlin.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel(){


//    private val _foo = MutableLiveData<Resource<List<UserModel>>>()
//    val foo: LiveData<Resource<List<UserModel>>> get() = _foo

    val getListLiveData: MutableLiveData<Resource<List<UserModel>>> = MutableLiveData<Resource<List<UserModel>>>()


    fun getEmployee() {

        mainRepository.getUserList().onEach { result ->

            Log.i("TAG", "getCoins: ")

            when (result.status) {
                Resource.Status.LOADING -> {
                    getListLiveData.value = Resource.Loading()
                }
                Resource.Status.SUCCESS -> {
                    getListLiveData.value = Resource.Success(result.data)
                }
                Resource.Status.ERROR -> {
                    getListLiveData.value = Resource.Error( result.message+"  error occured!!!")
                }
            }

        }.launchIn(viewModelScope)

    }


    fun onUserClear(){
        getListLiveData.value = null
    }

}