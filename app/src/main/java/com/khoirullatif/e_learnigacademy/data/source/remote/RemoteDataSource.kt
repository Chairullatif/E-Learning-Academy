package com.khoirullatif.e_learnigacademy.data.source.remote

import android.os.Handler
import android.os.Looper
import com.khoirullatif.e_learnigacademy.data.source.remote.response.ContentResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.CourseResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.ModuleResponse
import com.khoirullatif.e_learnigacademy.utils.EspressoIdlingResources
import com.khoirullatif.e_learnigacademy.utils.JsonHelper

// private constructor digunakan agar constructor hanya bisa digunakan untuk kelas ini saja
// jadi mau ngga mau kelas lain yg pengen pake kelas ini harus pakek RemoteDataSource.getInstance(helper)
// ngga bisa langsung RemoteDataSource(helper)
class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    //handler disini hanya digunakan untuk simulasi proses asynchronous

    private val handler = Handler(Looper.getMainLooper())

    //membuat singelton
    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    //sebelum menggunakan
//    fun getAllCourses(): List<CourseResponse> = jsonHelper.loadCourses()
//
//    fun getModules(courseId: String): List<ModuleResponse> = jsonHelper.loadModule(courseId)
//
//    fun getContent(moduleId: String): ContentResponse = jsonHelper.loadContent(moduleId)

    fun getAllCourses(callback: LoadCoursesCallback) {
        EspressoIdlingResources.increment()
        handler.postDelayed(
            {
            callback.onAllCoursesReceived(jsonHelper.loadCourses())
            EspressoIdlingResources.decrement()
            }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getModules(courseId: String, callback: LoadModulesCallback) {
        EspressoIdlingResources.increment()
        handler.postDelayed(
            {
                callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
                EspressoIdlingResources.decrement()
            }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getContent(moduleId: String, callback: LoadContentCallback) {
        EspressoIdlingResources.increment()
        handler.postDelayed(
            {
                callback.onContentReceived(jsonHelper.loadContent(moduleId))
                EspressoIdlingResources.decrement()
            }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponse: List<CourseResponse>)
    }

    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses: List<ModuleResponse>)
    }

    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }
}