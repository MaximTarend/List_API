package by.hometrainng.listapi.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.hometrainng.listapi.adapter.ItemAdapter
import by.hometrainng.listapi.addSpaceDecoration
import by.hometrainng.listapi.databinding.FragmentListBinding
import by.hometrainng.listapi.model.ListElement
import by.hometrainng.listapi.retrofit.FinalSpaceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ListFragment: Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ItemAdapter(requireContext()) {
            findNavController()
                .navigate(
                    ListFragmentDirections.toCharacter(it.id)
                )
        }
    }

//    private var isLoading = false
    private var currentCall: Call<List<ListElement.Character>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context) // default
        with (binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            loadFinalSpaceCharacters()
            recyclerView.addSpaceDecoration(DECORATION_SPACE)

            swipeLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()) // для заедержек есть другие способы реализации
                    .postDelayed(3000) {
                        swipeLayout.isRefreshing = false  // что бы кольцо обновления убиралось
                    }
                loadFinalSpaceCharacters()
            }
//           recyclerView.addPaginationScrollListener(layoutManager, ITEMS_TO_LOAD) {}
        }
    }

    private fun loadFinalSpaceCharacters() {
        if (currentCall == null) {
            currentCall = FinalSpaceService.provideFinalSpaceApi().getCharacters()
            currentCall?.enqueue(object : Callback<List<ListElement.Character>> {
                override fun onResponse(
                    call: Call<List<ListElement.Character>>,
                    response: Response<List<ListElement.Character>>
                ) {
                    if (response.isSuccessful) {
                        val characters = response.body() ?: return

                        adapter.submitList(characters)
                    } else {
                        showToastMessage(HttpException(response).message())
                    }
                    currentCall = null
                }

                override fun onFailure(call: Call<List<ListElement.Character>>, t: Throwable) {
                    currentCall = null
                    showToastMessage(UPLOAD_FAILURE)
                }
            })
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentCall?.cancel()
        _binding = null
    }

    companion object {
        private const val DECORATION_SPACE = 20
        private const val ITEMS_TO_LOAD = 20
        const val UPLOAD_FAILURE = "Upload failure"
    }
}