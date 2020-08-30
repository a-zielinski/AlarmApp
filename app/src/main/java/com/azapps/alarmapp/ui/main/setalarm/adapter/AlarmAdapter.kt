package com.azapps.alarmapp.ui.main.setalarm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azapps.alarmapp.R
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.databinding.RecyclerviewItemBinding

class AlarmAdapter internal constructor(
    context: Context,
    private val listener: (Alarm) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<Alarm>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerviewItemBinding.bind(itemView)
        //val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        val viewHolder = WordViewHolder(itemView)
        viewHolder.binding.btDelete.setOnClickListener {
            listener((alarms[viewHolder.adapterPosition]))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = alarms[position]
        holder.binding.tvTitle.text = current.title
        holder.binding.tvTime.text = current.startTime.toString()
    }

    internal fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    override fun getItemCount() = alarms.size
}