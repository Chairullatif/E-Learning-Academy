package com.khoirullatif.e_learnigacademy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.khoirullatif.e_learnigacademy.R
import com.khoirullatif.e_learnigacademy.data.ContentEntity
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.databinding.FragmentModuleContentBinding
import com.khoirullatif.e_learnigacademy.ui.reader.CourseReaderViewModel
import com.khoirullatif.e_learnigacademy.viewmodel.ViewModelFactory

class ModuleContentFragment : Fragment() {

    private lateinit var fragmentModuleContentBinding: FragmentModuleContentBinding

    companion object {
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentModuleContentBinding = FragmentModuleContentBinding.inflate(layoutInflater, container, false)
        return fragmentModuleContentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            //to using "share ViewModel" parameter this can replace with requireActivity
            //so this fragment will not create new ViewModel
            //            SEBELUM PAKE REPOSITORY
//            val viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[CourseReaderViewModel::class.java]

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            //didn't need moduleId again, because it was already set in ModuleListFragment (that's why the name is "share ViewModel")
            fragmentModuleContentBinding.progressBar.visibility = View.VISIBLE
            viewModel.getSelectedModule().observe(requireActivity(), { module ->
                fragmentModuleContentBinding.progressBar.visibility = View.GONE
                if (module != null) {
                    populateWebView(module)
                }
            })
//            val content = viewModel.getSelectedModule()
//            populateWebView(content)
        }
    }

    private fun populateWebView(content: ModuleEntity) {
        fragmentModuleContentBinding.webView.loadData(content.contentEntity?.content ?: "", "text/html", "UTF-8")
    }
}