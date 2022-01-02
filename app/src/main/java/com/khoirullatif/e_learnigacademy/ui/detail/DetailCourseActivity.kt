package com.khoirullatif.e_learnigacademy.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.khoirullatif.e_learnigacademy.R
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.databinding.ActivityDetailCourseBinding
import com.khoirullatif.e_learnigacademy.databinding.ContentDetailCourseBinding
import com.khoirullatif.e_learnigacademy.ui.reader.CourseReaderActivity
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class DetailCourseActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailCourseBinding
    private lateinit var detailContentBinding: ContentDetailCourseBinding

    companion object {
        const val EXTRA_COURSE = "extra_course"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCourseBinding.inflate(layoutInflater)
        detailContentBinding = binding.detailContent
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        //menambahkan up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = DetailCourseAdapter()

        val bundle = intent.extras

        if (bundle != null) {
            val courseId = bundle.getString(EXTRA_COURSE)
            if (courseId != null) {
                val modules = DataDummy.generateDummyModules(courseId)
                adapter.setModules(modules)
                for (course in DataDummy.generateDummyCourses()) {
                    if (course.courseId == courseId) {
                        populateCourse(course)
                    }
                }
            }
        }

        with(detailContentBinding.rvModule) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
            this.adapter = adapter
            setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(this@DetailCourseActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun populateCourse(courseEntity: CourseEntity) {
        detailContentBinding.textTitle.text = courseEntity.title
        detailContentBinding.textDescription.text = courseEntity.description
        detailContentBinding.textDate.text = resources.getString(R.string.deadline_date, courseEntity.deadline)
        Glide.with(this)
            .load(courseEntity.imagePath)
            .transform(RoundedCorners(20))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(detailContentBinding.imagePoster)
        detailContentBinding.btnStart.setOnClickListener {
            val intent = Intent(this, CourseReaderActivity::class.java)
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.courseId)
            startActivity(intent)
        }
    }
}