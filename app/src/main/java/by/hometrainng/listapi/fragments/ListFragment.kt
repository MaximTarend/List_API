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
import by.hometrainng.listapi.addSpaceDecoration
import by.hometrainng.listapi.databinding.FragmentListBinding
import by.hometrainng.listapi.model.ListElement

class ListFragment: Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ItemAdapter(requireContext())
    }

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

            val layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager

            recyclerView.addSpaceDecoration(DECORATION_SPACE)

            adapter.submitList(
                List(30) { ListElement.Character(it.toLong()) } + ListElement.Loading
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