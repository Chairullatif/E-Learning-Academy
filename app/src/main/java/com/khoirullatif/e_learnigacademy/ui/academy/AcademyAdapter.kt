package com.khoirullatif.e_learnigacademy.ui.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khoirullatif.e_learnigacademy.R
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.databinding.ItemsAcademyBinding
import com.khoirullatif.e_learnigacademy.ui.detail.DetailCourseActivity

class AcademyAdapter : RecyclerView.Adapter<AcademyAdapter.CourseViewHolder>() {

    private var listCourses = ArrayList<CourseEntity>()
    fun setCourses(courses: List<CourseEntity>?) {
        if (courses == null) return
        this.listCourses.clear()
        this.listCourses.addAll(courses)
    }

    class CourseViewHolder(private val binding: ItemsAcademyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseEntity) {
            with(binding){
                tvItemTitle.text = course.title
                tvItemDate.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)
                //handling event onclick on item in recyclerview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemsAcademyBinding = ItemsAcademyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(listCourses[position])
    }

    override fun getItemCount(): Int = listCourses.size
}