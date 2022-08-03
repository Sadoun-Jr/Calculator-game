package com.example.calculatorgame

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView

class GameOver : AppCompatActivity() {

    private val HISCORE = "HISCORE"
    private val SCORE = "score"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        supportActionBar?.hide()

        val playAgain = findViewById(R.id.playAgain) as Button
        val mainMenu = findViewById(R.id.mainMenu) as TextView
        val scoreText = findViewById<TextView>(R.id.scoreGameOver)
        val hiscoreTxt = findViewById<TextView>(R.id.hiscoreGameOver)

        val scoreStr = intent.getIntExtra(SCORE, 0)

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)

        scoreText.text = "Score: $scoreStr"
        hiscoreTxt.text = "HiScore: ${sharedPref.getInt(HISCORE, -1)}"

        playAgain.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        mainMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}