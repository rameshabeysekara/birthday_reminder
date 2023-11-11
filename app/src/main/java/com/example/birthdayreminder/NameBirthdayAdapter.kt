package com.example.birthdayreminder

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NameBirthdayAdapter(private val nameBirthdayList: List< MainActivity.NameBirthday >,private val onItemClick: (String) -> Unit ) :
    RecyclerView.Adapter<NameBirthdayAdapter.MyViewHolder>() {

    class MyViewHolder( itemView: View ) : RecyclerView.ViewHolder( itemView ) {

        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val birthdayTextView: TextView = itemView.findViewById(R.id.birthdayTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name_birthday, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val nameBirthday = nameBirthdayList[position]
        holder.nameTextView.text = nameBirthday.name
        holder.birthdayTextView.text = nameBirthday.birthday
        holder.itemView.setOnClickListener { onItemClick(nameBirthday.id) }
    }

    override fun getItemCount(): Int {
        return nameBirthdayList.size
    }
}
