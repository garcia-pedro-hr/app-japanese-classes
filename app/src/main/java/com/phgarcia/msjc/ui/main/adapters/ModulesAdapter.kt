package com.phgarcia.msjc.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phgarcia.msjc.R
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.ui.main.listeners.IOnItemClickListener

class ModulesAdapter(
    private val itemClickListener: IOnItemClickListener<Module>
) : RecyclerView.Adapter<ModulesAdapter.ViewHolder>() {

    var items = listOf<Module>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position, itemClickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun getItemCount(): Int = items.size

    fun select(position: Int) = itemClickListener.onItemClicked(items[position], position)

    class ViewHolder private constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        private val modulePortugueseTitle: TextView = itemView.findViewById(R.id.module_portuguese_title)
        private val moduleJapaneseTitle: TextView = itemView.findViewById(R.id.module_japanese_title)
        private val moduleNumber: TextView = itemView.findViewById(R.id.module_number)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_view_module, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: Module, position: Int, itemClickListener: IOnItemClickListener<Module>) {
            modulePortugueseTitle.text = item.ptTitle
            moduleJapaneseTitle.text = item.jpTitle
            moduleNumber.text = view.resources.getString(R.string.numbered_module, item.moduleNumber)
            view.setOnClickListener { itemClickListener.onItemClicked(item, position) }
        }
    }

}