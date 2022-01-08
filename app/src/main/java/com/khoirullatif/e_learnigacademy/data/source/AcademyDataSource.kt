package com.khoirullatif.e_learnigacademy.data.source

import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity

interface AcademyDataSource {

    fun getAllCourse(): List<CourseEntity>

    fun getBookmarkedCourse(): List<CourseEntity>

    fun getCourseWithModules(courseId: String): CourseEntity

    fun getAllModulesByCourse(courseId: String): List<ModuleEntity>

    fun getContent(courseId: String, moduleId: String): ModuleEntity
}