package com.khoirullatif.e_learnigacademy.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.khoirullatif.e_learnigacademy.data.ContentEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.data.source.AcademyRepository
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private lateinit var courseId: String
    private lateinit var moduleId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId = moduleId
    }

    fun getModules(): LiveData<List<ModuleEntity>> = academyRepository.getAllModulesByCourse(courseId)

    fun getSelectedModule(): LiveData<ModuleEntity> {
        // SEBELUM PAKAI REPOSITORY
//        lateinit var module: ModuleEntity
//        val moduleEntities = getModules()
//        for (moduleEntity in moduleEntities) {
//            if (moduleEntity.moduleId == moduleId) {
//                module = moduleEntity
//                module.contentEntity = ContentEntity("<h3 class=\\\"fr-text-bordered\\\">" + module.title + "</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
//                break
//            }
//        }
//        return module

        return academyRepository.getContent(courseId, moduleId)
    }
}