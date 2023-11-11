package com.example.birthdayreminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID


class MainActivity : AppCompatActivity() {

    data class NameBirthday(val id: String, val name: String, val birthday: String)

    private val nameBirthdayList = mutableListOf< NameBirthday >()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter      : NameBirthdayAdapter

    private val updateDataLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                loadData()
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        recyclerView = findViewById( R.id.namebirth_RV )
        adapter = NameBirthdayAdapter(nameBirthdayList) { id -> onItemClick(id) }

        recyclerView.adapter       = adapter
        recyclerView.layoutManager = LinearLayoutManager( this )

        loadData()

        val showDialogButton = findViewById< Button >( R.id.showDialogButton )
        showDialogButton.setOnClickListener {
            val dialogFragment = NameDateDialogFragment()
            dialogFragment.setOnNameDateSetListener { name, birthday ->
                val id = UUID.randomUUID().toString()
                nameBirthdayList.add( NameBirthday( id, name, birthday ) )
                adapter.notifyDataSetChanged()
                saveData()
            }
            dialogFragment.show(supportFragmentManager, "nameDateDialog")
        }

        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onItemClick(id: String) {
        val intent = Intent(this, UpdateDataActivity::class.java)
        intent.putExtra("id", id)
        updateDataLauncher.launch(intent)
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("birthdays", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        for (nameBirthday in nameBirthdayList) {
            editor.putString("name_${nameBirthday.id}", nameBirthday.name)
            editor.putString("birthday_${nameBirthday.id}", nameBirthday.birthday)
        }

        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("birthdays", MODE_PRIVATE)
        nameBirthdayList.clear()

        val uniqueIds = HashSet<String>()

        val keys = sharedPreferences.all.keys
        for (key in keys) {
            val splitKey = key.split("_")
            if (splitKey.size == 2) {
                val (dataType, id) = splitKey
                if (uniqueIds.add(id)) {
                    val name = sharedPreferences.getString("name_$id", null)
                    val birthday = sharedPreferences.getString("birthday_$id", null)

                    if (name != null && birthday != null) {
                        nameBirthdayList.add(NameBirthday(id, name, birthday))
                    }
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_DATA_REQUEST_CODE && resultCode == RESULT_OK) {
            loadData()
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val UPDATE_DATA_REQUEST_CODE = 1001
    }
}
