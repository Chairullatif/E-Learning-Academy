package com.khoirullatif.e_learnigacademy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.source.AcademyRepository
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourse(): LiveData<List<CourseEntity>> = academyRepository.getAllCourse()

}