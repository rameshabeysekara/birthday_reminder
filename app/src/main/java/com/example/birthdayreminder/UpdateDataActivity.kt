package com.example.birthdayreminder

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class UpdateDataActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var birthdayEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var backButton: Button

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        nameEditText = findViewById(R.id.nameEditText)
        birthdayEditText = findViewById(R.id.birthdayEditText)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)
        backButton = findViewById(R.id.backButton)

        id = intent.getStringExtra("id") ?: ""
        if (id.isEmpty()) {
            finish()
            return
        }

        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name_$id", "")
        val birthday = sharedPreferences.getString("birthday_$id", "")

        nameEditText.setText(name)

        birthdayEditText.setText(birthday)
        birthdayEditText.isFocusable = false
        birthdayEditText.isClickable = true
        birthdayEditText.setOnClickListener {
            showDatePickerDialog()
        }

        updateButton.setOnClickListener {
            updateData()
        }

        deleteButton.setOnClickListener {
            deleteData()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun updateData() {
        val updatedName = nameEditText.text.toString()
        val updatedBirthday = birthdayEditText.text.toString()

        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.remove("name_$id")
        editor.remove("birthday_$id")

        editor.putString("name_$id", updatedName)
        editor.putString("birthday_$id", updatedBirthday)

        editor.apply()

        setResult(RESULT_OK)
        finish()
    }

    private fun deleteData() {
        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.remove("name_$id")
        editor.remove("birthday_$id")
        editor.apply()

        setResult(RESULT_OK)
        finish()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val selectedDate = birthdayEditText.text.toString()
        val date = SimpleDateFormat("MMM. dd, yyyy", Locale.getDefault()).parse(selectedDate)

        calendar.time = date ?: Date()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val newDate = formatDate(selectedYear, selectedMonth, selectedDay)
                birthdayEditText.setText(newDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("MMM. dd, yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}

