package by.hometrainng.listapi.model

sealed class ListElement {

    data class CharacterItem(
        val id: Long = 111111,
        val login: String = "login"
            ): ListElement()

    object Loading: ListElement()
}