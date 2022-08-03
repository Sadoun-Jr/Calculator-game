package com.example.calculatorgame

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val DIFFICULTY = "DIFFICULTY"
    private val EASY = "EASY"
    private val TODDLER = "VERYEASY"
    private val MEDIUM = "MEDIUM"
    private val HARD = "HARD"
    private val EXTREME = "VERYHARD"
    private val ASIAN = "ASIAN"

    /**
     * Some instructions:
     * every 100 points you gain a life
     *
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        val difficulty = findViewById<Button>(R.id.difficulty)
        difficulty.setOnClickListener{
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
        }

        val startBtn = findViewById<Button>(R.id.start)
        startBtn.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        var instructions = findViewById<Button>(R.id.instructions)
        instructions.setOnClickListener{
            val intent = Intent(this, Instructions::class.java)
            startActivity(intent)
            finish()
        }

    }




    private fun getDifficulty(rg: RadioGroup) : Int{



        return 5
    }
}