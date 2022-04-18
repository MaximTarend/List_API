package by.hometrainng.listapi.retrofit

import by.hometrainng.listapi.model.Character
import by.hometrainng.listapi.model.ListElement
import retrofit2.http.GET

interface FinalSpaceApi {

    @GET("character")
    fun getCharacters(): retrofit2.Call<List<ListElement.CharacterItem>>
}