package com.example.coursesApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.coursesApp.ScreenState
import com.example.coursesApp.adapter.DelegatesAdapter
import com.example.coursesApp.adapter.courseItemDelegate

import com.example.coursesApp.databinding.FragmentHomeBinding
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: DelegatesAdapter<Course>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.courses.observe(viewLifecycleOwner) { state ->
            renderState(state)

        }
        binding.btnSort.setOnClickListener {
            viewModel.onSortClick()
            binding.recyclerCourses.scrollToPosition(0)
        }
    }

    private fun setupAdapter() {
        val manager = AdapterDelegatesManager<List<Course>>()
        manager.addDelegate(
            courseItemDelegate(
                onFavoriteClick = { viewModel.onFavoriteClick(it) },
                onItemClick = {})
        )
        adapter = DelegatesAdapter(manager)
        binding.recyclerCourses.adapter = adapter
    }

    private fun renderState(state: ScreenState) {
        when (state) {
            is ScreenState.Loading -> {
                binding.progressBar.isVisible = true
                binding.recyclerCourses.isVisible = false
            }

            is ScreenState.Content -> {
                binding.progressBar.isVisible = false
                binding.recyclerCourses.isVisible = true
                adapter.updateItems(state.courses)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}