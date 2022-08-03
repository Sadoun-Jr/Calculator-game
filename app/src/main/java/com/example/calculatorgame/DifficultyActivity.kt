package com.example.calculatorgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class DifficultyActivity: AppCompatActivity() {

    private val DIFFICULTY = "DIFFICULTY"
    private val EASY = "EASY"
    private val TODDLER = "VERYEASY"
    private val MEDIUM = "MEDIUM"
    private val HARD = "HARD"
    private val EXTREME = "VERYHARD"
    private val ASIAN = "ASIAN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficulty)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val veryEasy = findViewById<RadioButton>(R.id.veryEasy)as RadioButton
        val easy = findViewById<RadioButton>(R.id.easy) as RadioButton
        val medium = findViewById<RadioButton>(R.id.medium)as RadioButton
        val hard = findViewById<RadioButton>(R.id.hard)as RadioButton
        val veryHard = findViewById<RadioButton>(R.id.veryHard)as RadioButton
        val asian = findViewById<RadioButton>(R.id.asian)as RadioButton

        //make correct radio button already switched
        when(sharedPref.getString(DIFFICULTY, "99")) {
            TODDLER -> radioGroup.check(R.id.veryEasy)
            EASY -> radioGroup.check(R.id.easy)
            MEDIUM -> radioGroup.check(R.id.medium)
            HARD -> radioGroup.check(R.id.hard)
            EXTREME -> radioGroup.check(R.id.veryHard)
            ASIAN -> radioGroup.check(R.id.asian)
        }

        veryEasy.setOnClickListener{
            navigateToMain()
        }
        easy.setOnClickListener{
            navigateToMain()
        }
        medium.setOnClickListener{
            navigateToMain()
        }
        hard.setOnClickListener{
            navigateToMain()
        }
        veryHard.setOnClickListener{
            navigateToMain()
        }
        asian.setOnClickListener{
            navigateToMain()
        }

        //change difficulty
        radioGroup.setOnCheckedChangeListener{ _, i ->
            when (i) {
                R.id.veryEasy -> {
                    editor.putString(DIFFICULTY, TODDLER).apply()
                    veryEasy.isChecked
                    navigateToMain()
                }
                R.id.easy -> {
                    editor.putString(DIFFICULTY, EASY).apply()
                    easy.isChecked
                    navigateToMain()
                }
                R.id.medium -> {
                    editor.putString(DIFFICULTY, MEDIUM).apply()
                    medium.isChecked
                    navigateToMain()
                }
                R.id.hard -> {
                    editor.putString(DIFFICULTY, HARD).apply()
                    hard.isChecked
                    navigateToMain()
                }
                R.id.veryHard -> {
                    editor.putString(DIFFICULTY, EXTREME).apply()
                    veryHard.isChecked
                    navigateToMain()
                }
                R.id.asian -> {
                    editor.putString(DIFFICULTY, ASIAN).apply()
                    asian.isChecked
                    navigateToMain()
                }
            }
            Log.e("Radio", "Difficulty is ${sharedPref.getString(DIFFICULTY, "99")}")
        }
    }

    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}