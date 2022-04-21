package by.hometrainng.listapi.model

import com.google.gson.annotations.SerializedName

sealed class ListElement {

    data class Character(
        val id: Int = 0,
        val name: String,
        val species: String,
        val status: String,
        val gender: String,
        val hair: String,
        @SerializedName("img_url")
        val imageURL: String
    ): ListElement()

    object Loading: ListElement()
}