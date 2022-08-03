package com.example.calculatorgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Instructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

        super.onBackPressed()

    }
}