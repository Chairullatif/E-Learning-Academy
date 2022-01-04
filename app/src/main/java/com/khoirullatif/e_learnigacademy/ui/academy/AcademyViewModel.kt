package com.khoirullatif.e_learnigacademy.ui.academy

import androidx.lifecycle.ViewModel
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class AcademyViewModel : ViewModel() {

    fun getCourse(): List<CourseEntity> = DataDummy.generateDummyCourses()

}