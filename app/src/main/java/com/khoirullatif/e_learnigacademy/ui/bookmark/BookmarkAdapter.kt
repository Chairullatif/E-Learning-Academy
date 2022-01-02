package com.khoirullatif.e_learnigacademy.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khoirullatif.e_learnigacademy.R
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.databinding.ItemsBookmarkBinding
import com.khoirullatif.e_learnigacademy.ui.detail.DetailCourseActivity

class BookmarkAdapter(private val callback: BookmarkFragmentCallback) : RecyclerView.Adapter<BookmarkAdapter.CourseViewHolder> (){

    private val listCourse = ArrayList<CourseEntity>()

    fun setCourses(listCourse: List<CourseEntity>?) {
        if (listCourse == null) return
        this.listCourse.clear()
        this.listCourse.addAll(listCourse)
    }

    inner class CourseViewHolder(private val binding: ItemsBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseEntity) {
            with(binding) {
                tvItemTitle.text = course.title
                tvItemDate.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)
                imgShare.setOnClickListener { callback.onShareClick(course) }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.CourseViewHolder {
        val binding = ItemsBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.CourseViewHolder, position: Int) {
        return holder.bind(listCourse[position])
    }

    override fun getItemCount(): Int = listCourse.size
}