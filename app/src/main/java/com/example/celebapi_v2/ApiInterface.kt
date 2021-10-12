package com.example.celebapi_v2

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("celebrities/")

    suspend fun getData(): Response<List<Data>>


    @POST("celebrities/")


    fun postData(@Body data: Data): Call<Data>


    @PUT("test/{id}")
    fun update(@Path(
        "id") id: String,
               @Body data: Data
    ): Call<Data>

    @DELETE("/test/{id}")
    fun delete(@Path("id") id: String): Call<Void>

    ///https://dojo-recipes.herokuapp.com/celebrities/

}