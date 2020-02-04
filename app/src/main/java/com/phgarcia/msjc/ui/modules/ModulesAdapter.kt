package com.phgarcia.msjc.ui.modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.phgarcia.msjc.R
import com.phgarcia.msjc.models.Module

class ModulesAdapter : RecyclerView.Adapter<ModulesAdapter.ViewHolder>() {

    var data = listOf<Module>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val finishedMarker: MaterialRadioButton =
            itemView.findViewById(R.id.module_finished_marker)
        private val moduleNumber: TextView =
            itemView.findViewById(R.id.module_number)
        private val portugueseTitle: TextView =
            itemView.findViewById(R.id.module_portuguese_title)
        private val japaneseTitle: TextView =
            itemView.findViewById(R.id.module_japanese_title)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_view_module, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: Module) {
            val res = itemView.context.resources
            finishedMarker.isChecked = item.finished
            moduleNumber.text = res.getString(R.string.numbered_module, item.moduleNumber)
            portugueseTitle.text = item.ptTitle
            japaneseTitle.text = item.jpTitle
        }
    }

}
