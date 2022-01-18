package com.khoirullatif.e_learnigacademy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khoirullatif.e_learnigacademy.data.ContentEntity
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.data.source.remote.RemoteDataSource
import com.khoirullatif.e_learnigacademy.data.source.remote.response.ContentResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.CourseResponse
import com.khoirullatif.e_learnigacademy.data.source.remote.response.ModuleResponse

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource) : AcademyDataSource {

    //membuat singelton
    companion object {
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(dataSource: RemoteDataSource): AcademyRepository =
            instance ?: synchronized(this) {
                instance ?: AcademyRepository(dataSource).apply { instance = this }
            }
    }

// SEBELUM PAKAI LIVEDATA
//    override fun getAllCourse(): ArrayList<CourseEntity> {
//        val courseResponses = remoteDataSource.getAllCourses()
//        val courseList = ArrayList<CourseEntity>()
//        for (response in courseResponses) {
//            val course = CourseEntity(
//                response.id,
//                response.title,
//                response.description,
//                response.date,
//                false,
//                response.imagePath)
//
//            courseList.add(course)
//        }
//        return courseList
//    }
//
//    override fun getBookmarkedCourse(): ArrayList<CourseEntity> {
//        val courseResponses = remoteDataSource.getAllCourses()
//        val courseList = ArrayList<CourseEntity>()
//        for (response in courseResponses) {
//            val course = CourseEntity(
//                response.id,
//                response.title,
//                response.description,
//                response.date,
//                false,
//                response.imagePath)
//
//            courseList.add(course)
//        }
//        return courseList
//    }
//
//    override fun getCourseWithModules(courseId: String): CourseEntity {
//        val courseResponses = remoteDataSource.getAllCourses()
//        lateinit var course : CourseEntity
//        for (response in courseResponses) {
//            if (courseId == response.id) {
//                course = CourseEntity(
//                    response.id,
//                    response.title,
//                    response.description,
//                    response.date,
//                    false,
//                    response.imagePath)
//            }
//        }
//        return course
//    }
//
//    override fun getAllModulesByCourse(courseId: String): ArrayList<ModuleEntity> {
//        val moduleResponse = remoteDataSource.getModules(courseId)
//        val moduleList = ArrayList<ModuleEntity>()
//        for (response in moduleResponse) {
//            val module = ModuleEntity(
//                response.moduleId,
//                response.courseId,
//                response.title,
//                response.position,
//                false)
//
//            moduleList.add(module)
//        }
//        return moduleList
//    }
//
//    override fun getContent(courseId: String, moduleId: String): ModuleEntity {
//        val moduleResponse = remoteDataSource.getModules(courseId)
//        lateinit var module: ModuleEntity
//        for (response in moduleResponse) {
//            if (moduleId == response.moduleId) {
//                module = ModuleEntity(
//                    response.moduleId,
//                    response.courseId,
//                    response.title,
//                    response.position,
//                    false)
//                module.contentEntity = ContentEntity(remoteDataSource.getContent(moduleId).content)
//            }
//        }
//        return module
//    }

    //PAKAI LIVEDATA
    override fun getAllCourse(): LiveData<List<CourseEntity>> {
        val coursesResult = MutableLiveData<List<CourseEntity>>()

        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponse) {
                    val course = CourseEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath
                    )

                    courseList.add(course)
                }
                coursesResult.postValue(courseList)
            }
        })
        return coursesResult
    }

    override fun getBookmarkedCourse(): LiveData<List<CourseEntity>> {
        val coursesResult = MutableLiveData<List<CourseEntity>>()

        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponse) {
                    val course = CourseEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath
                    )

                    courseList.add(course)
                }
                coursesResult.postValue(courseList)
            }
        })
        return coursesResult
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity> {
        val courseResult = MutableLiveData<CourseEntity>()

        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
                lateinit var course: CourseEntity
                for (response in courseResponse) {
                    if (courseId == response.id) {
                         course = CourseEntity(
                            response.id,
                            response.title,
                            response.description,
                            response.date,
                            false,
                            response.imagePath
                        )
                    }
                }
                courseResult.postValue(course)
            }
        })
        return courseResult
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> {
        val moduleResults = MutableLiveData<List<ModuleEntity>>()

        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                val modulesList = ArrayList<ModuleEntity>()
                for (response in moduleResponses) {
                    val module = ModuleEntity(
                        response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false
                    )
                    modulesList.add(module)
                }
                moduleResults.postValue(modulesList)
            }
        })
        return moduleResults
    }

    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
        val moduleResult = MutableLiveData<ModuleEntity>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                lateinit var moduleEntity: ModuleEntity
                for (response in moduleResponses) {
                    if (response.moduleId == moduleId) {
                        moduleEntity = ModuleEntity(
                            response.moduleId,
                            response.courseId,
                            response.title,
                            response.position,
                            false
                        )
                        remoteDataSource.getContent(moduleId, object : RemoteDataSource.LoadContentCallback{
                            override fun onContentReceived(contentResponse: ContentResponse) {
                                moduleEntity.contentEntity = ContentEntity(contentResponse.content)
                                moduleResult.postValue(moduleEntity)
                            }
                        })
                        break
                    }
                }
            }
        })
        return moduleResult
    }
}
