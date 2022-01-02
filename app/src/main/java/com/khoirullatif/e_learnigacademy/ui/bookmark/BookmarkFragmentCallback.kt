package com.khoirullatif.e_learnigacademy.ui.bookmark

import com.khoirullatif.e_learnigacademy.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
