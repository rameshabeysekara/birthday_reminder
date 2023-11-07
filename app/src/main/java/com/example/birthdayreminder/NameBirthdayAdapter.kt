package com.example.birthdayreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NameBirthdayAdapter(private val nameBirthdayList: List< MainActivity.NameBirthday > ) :
    RecyclerView.Adapter<NameBirthdayAdapter.ViewHolder>() {

    class ViewHolder( itemView: View ) : RecyclerView.ViewHolder( itemView ) {

        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val birthdayTextView: TextView = itemView.findViewById(R.id.birthdayTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name_birthday, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameBirthday = nameBirthdayList[position]
        holder.nameTextView.text = nameBirthday.name
        holder.birthdayTextView.text = nameBirthday.birthday
    }

    override fun getItemCount(): Int {
        return nameBirthdayList.size
    }
}
