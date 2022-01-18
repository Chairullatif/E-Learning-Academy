package com.khoirullatif.e_learnigacademy.data.source

import androidx.lifecycle.LiveData
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity

interface AcademyDataSource {

    fun getAllCourse(): LiveData<List<CourseEntity>>

    fun getBookmarkedCourse(): LiveData<List<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<CourseEntity>

    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>>

    fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity>
}