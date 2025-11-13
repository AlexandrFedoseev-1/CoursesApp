package com.example.coursesApp.adapter

import com.example.coursesApp.R
import com.example.coursesApp.databinding.ItemCourseBinding
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.text.SimpleDateFormat
import java.util.Locale

fun courseItemDelegate(
    onFavoriteClick: (Course) -> Unit,
    onItemClick: (Course) -> Unit
) = adapterDelegateViewBinding<Course, Course, ItemCourseBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemCourseBinding.inflate(layoutInflater, parent, false)
    }) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

    bind {
        binding.tvTitleCourse.text = item.title
        binding.tvTextInfo.text = item.text
        binding.tvPrice.text = getString(R.string.price_format, item.price)
        binding.tvRate.text = item.rate
        binding.buttonFav.setImageResource(if (item.hasLike) R.drawable.fav_on else R.drawable.fav_off)
        val date = try {
            inputFormat.parse(item.publishDate)
        } catch (e: Exception) {
            null
        }

        binding.tvPublishDate.text = if (date != null) {
            outputFormat.format(date)
        } else {
            "—"
        }

        binding.buttonFav.setOnClickListener {
            onFavoriteClick(item)
        }

        binding.btnMore.setOnClickListener {
            onItemClick(item)
        }
    }

}