package by.hometrainng.listapi.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.hometrainng.listapi.adapter.ItemAdapter
import by.hometrainng.listapi.addPaginationScrollListener
import by.hometrainng.listapi.addSpaceDecoration
import by.hometrainng.listapi.databinding.FragmentListBinding
import by.hometrainng.listapi.model.Character
import by.hometrainng.listapi.model.ListElement
import by.hometrainng.listapi.retrofit.FinalSpaceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment: Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ItemAdapter(requireContext())
    }

    private var isLoading = false
    private var currentCall: Call<List<Character>>? = null

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

        with (binding) {
            swipeLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()) // для заедержек есть другие способы реализации
                    .postDelayed(3000) {
                        swipeLayout.isRefreshing = false  // что бы кольцо обновления убиралось
                    }
            }

            val layoutManager = LinearLayoutManager(view.context) // default
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager

            recyclerView.addSpaceDecoration(DECORATION_SPACE)
            recyclerView.addPaginationScrollListener(layoutManager, 20) {
                if (currentCall == null) {
                    currentCall = FinalSpaceService.provideFinalSpaceApi().getCharacters()
                    currentCall?.enqueue(object : Callback<List<Character>> {
                        override fun onResponse(
                            call: Call<List<Character>>,
                            response: Response<List<Character>>
                        ) {
                            val characters = response.body() ?: return
                            // adapter.submitList(characters)
                        }

                        override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                            currentCall = null
                        }
                    })
                }

                if (!isLoading) {
                    isLoading = true
                }
            }

            adapter.submitList(
                List(30) { ListElement.CharacterItem(it.toLong()) } + ListElement.Loading
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DECORATION_SPACE = 20
    }
}