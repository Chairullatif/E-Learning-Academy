package com.khoirullatif.e_learnigacademy.data.source.remote

import com.khoirullatif.e_learnigacademy.data.source.remote.response.ContentResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.CourseResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.ModuleResponse
import com.khoirullatif.e_learnigacademy.utils.JsonHelper

// private constructor digunakan agar constructor hanya bisa digunakan untuk kelas ini saja
// jadi mau ngga mau kelas lain yg pengen pake kelas ini harus pakek RemoteDataSource.getInstance(helper)
// ngga bisa langsung RemoteDataSource(helper)
class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    //membuat singelton
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllCourses(): List<CourseResponse> = jsonHelper.loadCourses()

    fun getModules(courseId: String): List<ModuleResponse> = jsonHelper.loadModule(courseId)

    fun getContent(moduleId: String): ContentResponse = jsonHelper.loadContent(moduleId)
}