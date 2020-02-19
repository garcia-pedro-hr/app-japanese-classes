package com.phgarcia.msjc.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.paris.extensions.style
import com.phgarcia.msjc.R
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.ui.main.listeners.IOnItemClickListener

class LessonsAdapter(
    private val itemClickListener: IOnItemClickListener<Lesson>
) : RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    var items = listOf<Lesson>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position, itemClickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun getItemCount(): Int = items.size

    class ViewHolder private constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        private val lessonPortugueseTitle: TextView = itemView.findViewById(R.id.lesson_portuguese_title)
        private val lessonJapaneseTitle: TextView = itemView.findViewById(R.id.lesson_japanese_title)
        private val lessonNumber: TextView = itemView.findViewById(R.id.lesson_number)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_view_lesson, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: Lesson, position: Int, itemClickListener: IOnItemClickListener<Lesson>) {
            lessonPortugueseTitle.text = item.ptTitle
            lessonJapaneseTitle.text = item.jpTitle
            lessonNumber.text = item.lessonNumber.toString()

            val style =
                if (item.finished) R.style.Widget_Msjc_LessonItem_Number_Complete
                else R.style.Widget_Msjc_LessonItem_Number_Incomplete
            lessonNumber.style(style)

            view.setOnClickListener { itemClickListener.onItemClicked(item, position) }
        }
    }

}