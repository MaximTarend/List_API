package by.hometrainng.listapi.model

import com.google.gson.annotations.SerializedName

data class Character(
    val name: String,
    val species: String,
    @SerializedName("img_url")
    val imageURL: String
)