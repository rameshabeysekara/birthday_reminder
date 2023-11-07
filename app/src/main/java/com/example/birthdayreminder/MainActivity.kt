package com.example.birthdayreminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter


class MainActivity : AppCompatActivity() {

    data class NameBirthday( val name: String, val birthday: String )

    private val nameBirthdayList = mutableListOf< NameBirthday >()

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter      : Adapter<ViewHolder>

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        val showDialogButton = findViewById< Button >( R.id.showDialogButton )

        recyclerView = findViewById( R.id.namebirth_RV )
        adapter = object : Adapter< ViewHolder >() {

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ViewHolder {

                val view = LayoutInflater.from( parent.context ).inflate( R.layout.item_name_birthday, parent, false )
                return MyViewHolder( view )

            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                val nameBirthday = nameBirthdayList[ position ]
                val myViewHolder = holder as MyViewHolder
                myViewHolder.nameTextView.text     = nameBirthday.name
                myViewHolder.birthdayTextView.text = nameBirthday.birthday

            }

            override fun getItemCount(): Int {

                return nameBirthdayList.size

            }
        }

        recyclerView.adapter       = adapter
        recyclerView.layoutManager = LinearLayoutManager( this )

        showDialogButton.setOnClickListener {
            val dialogFragment = NameDateDialogFragment()
            dialogFragment.setOnNameDateSetListener { name, birthday ->
                nameBirthdayList.add( NameBirthday( name, birthday ) )
                adapter.notifyDataSetChanged()
            }
            dialogFragment.show(supportFragmentManager, "nameDateDialog")
        }

    }

    class MyViewHolder( itemView: View ) : ViewHolder( itemView )  {

        val nameTextView     : TextView = itemView.findViewById( R.id.nameTextView )
        val birthdayTextView : TextView = itemView.findViewById( R.id.birthdayTextView )

    }



//    fun updateNameAndDate(name: String, date: String) {
//
//        val nameAndDateTextView  = findViewById< TextView >( R.id.nameAndDateTextView )
//        nameAndDateTextView.text = "Name: $name\nBirthdate: $date"
//
//    }

    //        showDialogButton.setOnClickListener {
//
//            val dialogFragment = NameDateDialogFragment()
//            dialogFragment.setOnNameDateSetListener { name, birthday ->
//                nameBirthdayList.add( NameBirthday( name, birthday) )
//                adapter.notifyDataSetChanged()
//
//            }
//
//            dialogFragment.show(supportFragmentManager, "nameDateDialog")
//
//        }

}
