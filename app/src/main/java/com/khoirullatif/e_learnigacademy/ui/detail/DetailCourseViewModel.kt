package com.khoirullatif.e_learnigacademy.ui.detail

import androidx.lifecycle.ViewModel
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.data.source.AcademyRepository
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    private lateinit var courseId: String

    fun setSelectedCourse(id: String) {
        this.courseId = id
    }

    fun getCourse(): CourseEntity {
        //SEBELUM PAKAI REPOSITORY
//        lateinit var course: CourseEntity
//        val courseEntities = DataDummy.generateDummyCourses()
//        for (courseEntity in courseEntities) {
//            if (courseEntity.courseId == courseId) {
//                course = courseEntity
//            }
//        }
//        return course
        return academyRepository.getCourseWithModules(courseId)
    }

    fun getModule(): List<ModuleEntity> = academyRepository.getAllModulesByCourse(courseId)
}