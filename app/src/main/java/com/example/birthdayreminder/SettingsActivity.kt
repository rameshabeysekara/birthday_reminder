package com.example.birthdayreminder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton: Button = findViewById(R.id.backButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            resetData()
        }
    }

    private fun resetData() {
        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainActivityIntent)
        finish()
    }
}
