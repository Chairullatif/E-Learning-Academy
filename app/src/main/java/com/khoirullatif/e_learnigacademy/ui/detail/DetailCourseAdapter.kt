package com.khoirullatif.e_learnigacademy.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.databinding.ItemsModuleListBinding

class DetailCourseAdapter : RecyclerView.Adapter<DetailCourseAdapter.DetailCourseViewHolder> () {

    private val listModules = ArrayList<ModuleEntity>()

    fun setModules(listModules: List<ModuleEntity>?) {
        if (listModules == null) return
        this.listModules.clear()
        this.listModules.addAll(listModules)
    }

    inner class DetailCourseViewHolder(private val binding: ItemsModuleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: ModuleEntity) {
            binding.textModuleTitle.text = module.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailCourseAdapter.DetailCourseViewHolder {
        val binding = ItemsModuleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailCourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DetailCourseAdapter.DetailCourseViewHolder,
        position: Int
    ) {
        return holder.bind(listModules[position])
    }

    override fun getItemCount(): Int = listModules.size
}