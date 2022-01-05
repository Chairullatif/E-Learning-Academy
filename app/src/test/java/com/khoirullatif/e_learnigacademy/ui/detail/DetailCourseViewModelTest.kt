package com.khoirullatif.e_learnigacademy.ui.detail

import com.khoirullatif.e_learnigacademy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DetailCourseViewModelTest {

    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel()
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        val course = viewModel.getCourse()
        assertNotNull(course)
        assertEquals(dummyCourse.courseId, course.courseId)
        assertEquals(dummyCourse.title, course.title)
        assertEquals(dummyCourse.description, course.description)
        assertEquals(dummyCourse.deadline, course.deadline)
        assertEquals(dummyCourse.imagePath, course.imagePath)
    }

    @Test
    fun getModule() {
        val modules = viewModel.getModule()
        assertNotNull(modules)
        assertEquals(7, modules.size.toLong())
    }
}