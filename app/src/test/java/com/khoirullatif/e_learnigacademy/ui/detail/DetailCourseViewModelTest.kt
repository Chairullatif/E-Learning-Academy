package com.khoirullatif.e_learnigacademy.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.data.source.AcademyRepository
import com.khoirullatif.e_learnigacademy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {

    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerCourse: Observer<CourseEntity>

    @Mock
    private lateinit var observerModules: Observer<List<ModuleEntity>>

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        val courseMutable = MutableLiveData<CourseEntity>()
        courseMutable.value = dummyCourse

        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(courseMutable)
        val course = viewModel.getCourse().value
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(course)
        assertEquals(dummyCourse.courseId, course?.courseId)
        assertEquals(dummyCourse.title, course?.title)
        assertEquals(dummyCourse.description, course?.description)
        assertEquals(dummyCourse.deadline, course?.deadline)
        assertEquals(dummyCourse.imagePath, course?.imagePath)

        viewModel.getCourse().observeForever(observerCourse)
        verify(observerCourse).onChanged(dummyCourse)
    }

    @Test
    fun getModule() {
        val modulesMutable = MutableLiveData<List<ModuleEntity>>()
        val dummyModules = DataDummy.generateDummyModules(courseId)
        modulesMutable.value = dummyModules

        `when`(academyRepository.getAllModulesByCourse(courseId)).thenReturn(modulesMutable)
        val modules = viewModel.getModule().value
        verify(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(modules)
        assertEquals(7, modules?.size)

        viewModel.getModule().observeForever(observerModules)
        verify(observerModules).onChanged(dummyModules)
    }
}