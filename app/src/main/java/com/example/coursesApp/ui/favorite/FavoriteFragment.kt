package com.example.coursesApp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coursesApp.databinding.FragmentFavoriteBinding
import com.example.coursesApp.adapter.DelegatesAdapter
import com.example.coursesApp.adapter.courseItemDelegate
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var adapter: DelegatesAdapter<Course>
    private val viewModel by viewModel<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.updateItems(courses)
        }
    }

    private fun setupAdapter() {
        val manager = AdapterDelegatesManager<List<Course>>()
        manager.addDelegate(courseItemDelegate(
            onFavoriteClick = {
                viewModel.onFavoriteClick(it)
            },
            onItemClick = {}))
        adapter = DelegatesAdapter(manager)
        binding.recyclerCourses.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
