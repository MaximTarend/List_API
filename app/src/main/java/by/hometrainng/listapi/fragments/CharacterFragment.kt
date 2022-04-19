package by.hometrainng.listapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.hometrainng.listapi.databinding.FragmentCharacterBinding
import by.hometrainng.listapi.model.ListElement
import by.hometrainng.listapi.retrofit.FinalSpaceService
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterFragment: Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<CharacterFragmentArgs>()
    private var currentCall: Call<ListElement.Character>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCharacterBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding) {
            val characterId = args.characterId.toString()

            toolbarCharacter.setupWithNavController(findNavController())

            if (currentCall == null) {
                currentCall = FinalSpaceService.provideFinalSpaceApi().getCharacterDetails(characterId)
                currentCall?.enqueue(object : Callback<ListElement.Character> {
                    override fun onResponse(
                        call: Call<ListElement.Character>,
                        response: Response<ListElement.Character>
                    ) {
                        if (response.isSuccessful) {
                            val character = response.body() ?: return
                            image.load(character.imageURL)
                            name.text = character.name
                            species.text = "Species: " + character.species
                            status.text = "Status: " + character.status
                            gender.text = "Gender: " + character.gender
                            hair.text = "Hair: " + character.hair

                            currentCall = null
                        } else {
                            Toast.makeText(context, "Response failure", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ListElement.Character>, t: Throwable) {
                        currentCall = null
                        Toast.makeText(context, "Upload failure", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}