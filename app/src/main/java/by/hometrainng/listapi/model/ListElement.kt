package by.hometrainng.listapi.model

import com.google.gson.annotations.SerializedName

sealed class ListElement {

    data class CharacterItem(
        val name: String,
        val species: String,
        @SerializedName("img_url")
        val imageURL: String
    ): ListElement()

    object Loading: ListElement()
}