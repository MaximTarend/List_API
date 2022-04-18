package by.hometrainng.listapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object FinalSpaceService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://finalspaceapi.com/api/v0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val finalSpaceApi by lazy { retrofit.create<FinalSpaceApi>() }

    fun provideFinalSpaceApi(): FinalSpaceApi {
        return finalSpaceApi
    }
}