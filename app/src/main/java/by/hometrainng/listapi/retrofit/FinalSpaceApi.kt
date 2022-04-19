package by.hometrainng.listapi.retrofit

import by.hometrainng.listapi.model.ListElement
import retrofit2.http.GET
import retrofit2.http.Path

interface FinalSpaceApi {

    @GET("character")
    fun getCharacters(): retrofit2.Call<List<ListElement.Character>>

    @GET("character/{id}")
    fun getCharacterDetails(
        @Path("id") id: String
    ): retrofit2.Call<ListElement.Character>


}