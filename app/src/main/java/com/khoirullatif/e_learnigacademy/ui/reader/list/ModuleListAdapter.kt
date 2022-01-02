package com.khoirullatif.e_learnigacademy.ui.reader.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khoirullatif.e_learnigacademy.data.ModuleEntity
import com.khoirullatif.e_learnigacademy.databinding.ItemsModuleListBinding

class ModuleListAdapter internal constructor(private val listener: MyAdapterClickListener) : RecyclerView.Adapter<ModuleListAdapter.ModuleListViewHolder>() {

    private var listModule = ArrayList<ModuleEntity>()

    internal fun setModules(modules: List<ModuleEntity>?) {
        if (modules != null) {
            this.listModule.clear()
            this.listModule.addAll(modules)
        }
    }

    inner class ModuleListViewHolder(private val binding: ItemsModuleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: ModuleEntity) {
            binding.textModuleTitle.text = module.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleListViewHolder {
        val itemsModuleListBinding = ItemsModuleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleListViewHolder(itemsModuleListBinding)
    }

    override fun onBindViewHolder(holder: ModuleListViewHolder, position: Int) {
        holder.bind(listModule[position])
        holder.itemView.setOnClickListener {
            listener.onItemClicked(holder.adapterPosition, listModule[holder.adapterPosition].moduleId)
        }
    }

    override fun getItemCount(): Int = listModule.size
}

internal interface MyAdapterClickListener {
    fun onItemClicked(position: Int, moduleId: String)
}