package com.khoirullatif.e_learnigacademy.ui.bookmark

import androidx.lifecycle.ViewModel
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.data.source.AcademyRepository
import com.khoirullatif.e_learnigacademy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): List<CourseEntity> = academyRepository.getBookmarkedCourse()
}