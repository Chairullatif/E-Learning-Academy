package com.khoirullatif.e_learnigacademy.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khoirullatif.e_learnigacademy.data.source.remote.RemoteDataSource
import com.khoirullatif.e_learnigacademy.utils.DataDummy
import com.khoirullatif.e_learnigacademy.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.verify

class AcademyRepositoryTest {

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getAllCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
        }.`when`(remote).getAllCourses(any())
        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourse())
        verify(remote).getAllCourses(any())
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getAllModulesByCourse() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponses)
        }.`when`(remote).getModules(eq(courseId), any())

        val moduleEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))
        verify(remote).getModules(eq(courseId), any())
        assertNotNull(moduleEntities)
        assertEquals(moduleResponses.size.toLong(), moduleEntities.size.toLong())
    }

    @Test
    fun getBookmarkedCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
        }.`when`(remote).getAllCourses(any())

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourse())
        verify(remote).getAllCourses(any())
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getContent() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponses)
        }.`when`(remote).getModules(eq(courseId), any())

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadContentCallback)
                .onContentReceived(content)
        }.`when`(remote).getContent(eq(moduleId), any())

        val resultModule = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId))
        verify(remote).getModules(eq(courseId), any())
        verify(remote).getContent(eq(moduleId), any())
        assertNotNull(resultModule)
        assertNotNull(resultModule.contentEntity)
        assertNotNull(resultModule.contentEntity?.content)
        assertEquals(content.content, resultModule.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
        }.`when`(remote).getAllCourses(any())
        val resultCourse = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
        verify(remote).getAllCourses(any())
        assertNotNull(resultCourse)
        assertNotNull(resultCourse.title)
        assertEquals(courseResponses[0].title, resultCourse.title)
    }
}