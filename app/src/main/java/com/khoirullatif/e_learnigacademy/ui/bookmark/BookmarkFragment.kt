package com.khoirullatif.e_learnigacademy.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoirullatif.e_learnigacademy.R
import com.khoirullatif.e_learnigacademy.data.CourseEntity
import com.khoirullatif.e_learnigacademy.databinding.FragmentBookmarkBinding
import com.khoirullatif.e_learnigacademy.utils.DataDummy
import com.khoirullatif.e_learnigacademy.viewmodel.ViewModelFactory

class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    private lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
//            SEBELUM PAKE REPOSITORY
//            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[BookmarkViewModel::class.java]

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]

            val adapter = BookmarkAdapter(this)
//            val course = viewModel.getBookmarks()
//            adapter.setCourses(course)
            fragmentBookmarkBinding.progressBar.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(requireActivity(), { courses ->
                fragmentBookmarkBinding.progressBar.visibility = View.GONE
                adapter.setCourses(courses)
                adapter.notifyDataSetChanged()
            })

            with(fragmentBookmarkBinding.rvBookmark) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan Course ini sekarang!")
                .setText(resources.getString(R.string.share_text, course.title))
                .startChooser()
        }
    }

}