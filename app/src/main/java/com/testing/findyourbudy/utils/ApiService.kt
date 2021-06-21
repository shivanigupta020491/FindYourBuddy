package com.testing.findyourbudy.utils

import com.testing.findyourbudy.data.ChatResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("message")
    fun getMessageForChatbox(): Call<List<ChatResponse>>


}