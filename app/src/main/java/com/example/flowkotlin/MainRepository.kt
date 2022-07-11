package com.example.flowkotlin

import com.example.cleanarchitecture_xml.data.remote.ApiService
import com.example.flowkotlin.common.Resource
import com.example.flowkotlin.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {


//    suspend fun getShowTvList() = apiService.getUserList()

    fun getUserList(): Flow<Resource<List<UserModel>>> = flow {

        try {

            emit(Resource.Loading())
            val employee = apiService.getUserList()
            emit(Resource.Success(employee))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error occured", null))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "check your internet connection", null))
        }
    }




}