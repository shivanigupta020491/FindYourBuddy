package com.testing.findyourbudy

import com.testing.findyourbudy.utils.ApiConstant
import com.testing.findyourbudy.utils.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private  var myApi:ApiService? = null

    fun provideRetrofit():Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstant.MAIN_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    fun getApi():ApiService{
        myApi = provideRetrofit().create(ApiService::class.java)
        return myApi!!
    }
}