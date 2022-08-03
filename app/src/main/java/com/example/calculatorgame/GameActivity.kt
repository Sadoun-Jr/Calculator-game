package com.example.calculatorgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity() {

    var firstNumberStr : Int = 0
    var secondNumberStr : Int = 0
    var firstOperatorStr : String = ""
    var secondOperatorStr : String = ""
    var lifeCounter : Int = 3
    var thirdNumberStr : Int = 0
    var scoreStr : Int = 0
    var skipCounterStr : Int = 0
    var userAnswer : Int = 0
    var correctAnswerStr : Int = 0
    var timerMillis :Long = 60 * 1000
    var numberOfQuestions : Int = 1
    var canChangeTimer : Boolean = true
    var amountOfTimesTimerChanged : Int = 0
    var numberOfCorrectAnswers: Int = 0
    var numberOfWrongAnswers: Int = 0
    var numberOfSkippedAnswers : Int = 0
    var lastAnswerWasCorrect : Boolean = false
    var lastAnswerWasFalse : Boolean = false
    private val ADD = "ADD"
    private val SUBTRACT = "SUBTRACT"
    private val MULTIPLY = "MULTIPLY"
    private val DIVIDE = "DIVIDE"
    private val DIFFICULTY = "DIFFICULTY"
    private val EASY = "EASY"
    private val TODDLER = "VERYEASY"
    private val MEDIUM = "MEDIUM"
    private val HARD = "HARD"
    private val EXTREME = "VERYHARD"
    private val ASIAN = "ASIAN"
    private val HISCORE = "HISCORE"
    private val SCORE = "score"
    val operationArray : ArrayList<String> = ArrayList<String>()
    private lateinit var firstDigit : TextView
    private lateinit var secondDigit : TextView
    private lateinit var thirdDigit : TextView
    private lateinit var firstOperator : TextView
    private lateinit var secondOperator : TextView
    private lateinit var firstArc : TextView
    private lateinit var secondArc : TextView
    private lateinit var answerField : TextView
    private lateinit var scoreText : TextView
    private lateinit var correctAnswerTxt : TextView
    private lateinit var skip : Button
    private lateinit var check : Button
    private lateinit var checkCross : ImageView
    private lateinit var lifes : ImageView
    private lateinit var timerText : TextView
    private lateinit var testCorrectAnswer: TextView
    private lateinit var timer: CountDownTimer
    private val LOG_TAG = "Game activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        firstDigit = findViewById<TextView>(R.id.firstDigit)
        secondDigit = findViewById<TextView>(R.id.secondDigit)
        thirdDigit = findViewById<TextView>(R.id.thirdDigit)
        firstOperator = findViewById<TextView>(R.id.firstOperator)
        secondOperator = findViewById<TextView>(R.id.secondOperator)
        firstArc = findViewById<TextView>(R.id.firstArc)
        secondArc = findViewById<TextView>(R.id.secondArc)
        scoreText = findViewById<TextView>(R.id.scoreTxt)
        answerField = findViewById<EditText>(R.id.answerField)
        answerField.isCursorVisible = false
        answerField.maxWidth = answerField.width
        check = findViewById<Button>(R.id.check)
        skip = findViewById<Button>(R.id.skip)
        skip.visibility = View.INVISIBLE

        supportActionBar?.hide()

        skip.setBackgroundResource(R.drawable.transparent_img)
        checkCross = findViewById(R.id.checkCross)
        checkCross.setImageResource(R.drawable.transparent_img)
        Log.e(LOG_TAG, "CheckCross should have a transparent now")
        correctAnswerTxt = findViewById(R.id.correctAnswer)
        testCorrectAnswer = findViewById<TextView>(R.id.testCorrectAnswer)

        lifes = findViewById(R.id.life)
        timerText = findViewById<TextView>(R.id.timer)

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        Log.e(LOG_TAG, "Starting game, difficulty ${sharedPref.getString(DIFFICULTY, EASY)!!}")

        //arraylist to randomise the operation used
        operationArray.add(ADD)
        operationArray.add(SUBTRACT)
        operationArray.add(MULTIPLY)

        val difficulty = sharedPref.getString(DIFFICULTY, "EASY")!!

        Log.e(LOG_TAG, "Step 1: initiating random numbers")
        initiateRandomNumbers(sharedPref.getString(DIFFICULTY, EASY)!!)

        check.setOnClickListener{

            checkTheAnswerAccordingToDifficulty(difficulty)

        }

        skip.setOnClickListener{
            Log.e(LOG_TAG, "Skipped")
            skipQuestion()

        }
    }

    private fun checkTheAnswerAccordingToDifficulty(difficulty: String) {
        when(difficulty){
            TODDLER -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectTwoDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
            EASY -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectTwoDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
            MEDIUM -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectThreeDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
            HARD -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectThreeDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
            EXTREME -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectThreeDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
            ASIAN -> {
                if(answerField.text.toString().isNotEmpty()) {
                    val userAnswer = answerField.text.toString().trimStart().trimEnd().toInt()
                    Log.e(LOG_TAG, "Step 5: Grabbing user answer, it is $userAnswer")
                    val isUserCorrect = checkIfUserIsCorrectThreeDigits(userAnswer)
                    if (isUserCorrect){
                        displayCorrectAnswer(difficulty)
                    } else{
                        displayWrongAnswer(difficulty)
                    }
                }else{
                    displayWrongAnswer(difficulty)
                }
            }
        }
        //todo: [5]

    }

    private fun checkIfUserIsCorrectThreeDigits(userAnswer: Int): Boolean {
        val correctAnswer = calculateCorrectAnswerThreeDigits()
        Log.e(LOG_TAG, "Step 6: Checking user answer, it is ${userAnswer == correctAnswer}")
        //TODO: [4]

        return userAnswer == correctAnswer
    }

    private fun skipQuestion() {
        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        removeASkip()
        lastAnswerWasFalse = true
        lastAnswerWasCorrect = false
        correctAnswerTxt.text = ""
        numberOfSkippedAnswers +=1
        incrementAndDisplayLiveScore(scoreStr)
        timer.cancel()
        checkCross.setImageResource(R.drawable.transparent_img)
        nextQuestion(sharedPref.getString(DIFFICULTY, "EASY")!!)

    }

    private fun removeASkip() {
        skipCounterStr -= 1
        if (scoreStr == 0){
            skip.visibility = View.INVISIBLE
        }
        skip.text = "Skip ($skipCounterStr)"
        Log.e(LOG_TAG, "Skips available: $skipCounterStr")

    }

    //7-2
    private fun displayCorrectAnswer(difficulty: String) {
        checkCross.setImageResource(R.drawable.eo_circle_green_white_checkmark)
        Log.e(LOG_TAG, "CheckCross should have a green mark now")
        correctAnswerTxt.text = ""
        incrementScoreByDifficulty(difficulty)
        numberOfCorrectAnswers += 1
        checkToAddSkip()
        checkToAddALife()
        incrementAndDisplayLiveScore(scoreStr)
        lastAnswerWasCorrect = true
        lastAnswerWasFalse = false
        timer.cancel()
        nextQuestion(difficulty)
    }

    private fun checkToAddALife() {
        if ((scoreStr.toFloat() / 100) == (scoreStr / 100).toFloat()){
            gainAnExtraLife()
        }
    }

    private fun gainAnExtraLife() {
        when(lifeCounter){
            3 -> Toast.makeText(this, "You already have max lives", Toast.LENGTH_SHORT).show()
            2 -> {
                Toast.makeText(this, "Extra life gained", Toast.LENGTH_SHORT).show()
                lifeCounter += 1
                lifes.setImageResource(R.drawable.three_live)
            }
            1 -> {
                Toast.makeText(this, "Extra life gained", Toast.LENGTH_SHORT).show()
                lifeCounter += 1
                lifes.setImageResource(R.drawable.two_lives)
            }
        }

    }

    private fun incrementScoreByDifficulty(difficulty: String) {
        when(difficulty){
            TODDLER -> scoreStr += 1
            EASY -> scoreStr += 2
            MEDIUM -> scoreStr += 4
            HARD -> scoreStr += 5
            EXTREME -> scoreStr += 10
            ASIAN -> scoreStr += 20
        }
    }

    //8-2
    private fun checkToAddSkip() {
        if((scoreStr.toFloat()/20) == (scoreStr/20).toFloat()){
            if(scoreStr > 1){
                skip.visibility = View.VISIBLE
            }
            skip.setBackgroundResource(R.drawable.button_custom)
            skipCounterStr += 1
            skip.text = "Skip ($skipCounterStr)"
        }
        if (scoreStr < 1){
            skip.visibility = View.INVISIBLE
            skipCounterStr = 0

        }
    }

    //1277
    private fun incrementAndDisplayLiveScore(newScore : Int) {
        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        val oldHiScore = sharedPref.getInt(HISCORE, 0)
        Log.e(LOG_TAG, "Current hiscore is: $oldHiScore, new hiscore is $newScore")

        if (newScore>=oldHiScore){
            editor.putInt(HISCORE, newScore).apply()
            scoreText.text = "NEW HISCORE: ${sharedPref.getInt(HISCORE, 0)}"
            Log.e(LOG_TAG, "Should show hiscore now")
        } else {
            scoreText.text = "Score: $newScore"
        }

    }

    //7-1
    private fun displayWrongAnswer(difficulty: String) {
        checkCross.setImageResource(R.drawable._40px_cross_red_circle)
        Log.e(LOG_TAG, "CheckCross should have a Red cross now")
        numberOfWrongAnswers +=1
        correctAnswerTxt.visibility = View.VISIBLE

        when(difficulty){
            TODDLER -> correctAnswerTxt.text = "Correct Answer: ${(firstNumberStr + secondNumberStr).toString()}"
            EASY -> correctAnswerTxt.text = "Correct Answer: ${(firstNumberStr + secondNumberStr).toString()}"
            MEDIUM -> correctAnswerTxt.text = "Correct Answer: $correctAnswerStr"
            HARD -> correctAnswerTxt.text = "Correct Answer: $correctAnswerStr"
            EXTREME -> correctAnswerTxt.text = "Correct Answer: $correctAnswerStr"
            ASIAN -> correctAnswerTxt.text = "Correct Answer: $correctAnswerStr"

            //todo: [6]
        }
        incrementAndDisplayLiveScore(scoreStr)
        removeALife()
        lastAnswerWasCorrect = false
        lastAnswerWasFalse = true
        timer.cancel()

        nextQuestion(difficulty)
    }

    //8-1
    private fun removeALife() {
        when(lifeCounter){
            3 -> {
                lifes.setImageResource(R.drawable.two_lives)
                lifeCounter -= 1
            }
            2 -> {
                lifes.setImageResource(R.drawable.one_life)

                lifeCounter -= 1
            }
            1 -> {
                showGameOverDialog()
            }
        }
    }

    //10
    private fun resetStats(){
        numberOfCorrectAnswers = 0
        firstNumberStr  = 0
        secondNumberStr  = 0
        firstOperatorStr = ""
        secondOperatorStr  = ""
        lifeCounter  = 3
        thirdNumberStr  = 0
        scoreStr  = 0
        skipCounterStr = 0
        userAnswer = 0
        lifes.setImageResource(R.drawable.three_live)
        Log.e(LOG_TAG, "CheckCross should have a transparent now")
        incrementAndDisplayLiveScore(0)
        correctAnswerTxt.text = ""

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        nextQuestion(sharedPref.getString(DIFFICULTY, "EASY")!!)
    }

    //9
    private fun showGameOverDialog() {

        val intent= Intent(this, GameOver::class.java)
        intent.putExtra(SCORE, scoreStr)
        startActivity(intent)
        finish()
    }

    //6
    private fun nextQuestion(difficulty: String) {
        Log.e(LOG_TAG, "Step 6: Next question")
        numberOfQuestions +=1
        answerField.text = ""
        initiateRandomNumbers(difficulty)
    }

    //5
    private fun checkIfUserIsCorrectTwoDigits(userAnswer: Int) : Boolean {
        val correctAnswer = calculateCorrectAnswerTwoDigits()
        Log.e(LOG_TAG, "Step 6: Checking user answer, it is ${userAnswer == correctAnswer}")

        return userAnswer == correctAnswer

    }

    //1
    private fun initiateRandomNumbers(difficulty : String) {
        when (difficulty){
            TODDLER -> {
                firstOperatorStr = ADD
                firstNumberStr = (0..9).random()
                secondNumberStr = (0..9).random()
                Log.e(LOG_TAG, "Random numbers are $firstNumberStr and $secondNumberStr")
                Log.e(LOG_TAG, "Step 2: hiding views")
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsTwoDigits()
            }
            EASY -> {
                firstOperatorStr = ADD
                firstNumberStr = (10..99).random()
                secondNumberStr = (10..99).random()
                Log.e(LOG_TAG, "Random numbers are $firstNumberStr and $secondNumberStr")
                Log.e(LOG_TAG, "Step 2: hiding views")
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsTwoDigits()
            }
            MEDIUM -> {
                firstOperatorStr = ADD
                secondOperatorStr = SUBTRACT
                firstNumberStr = (50..99).random()
                secondNumberStr = (20..99).random()
                thirdNumberStr = (10..50).random()
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsThreeDigits()
            }
            HARD -> {
                firstOperatorStr = operationArray[(0..1).random()]
                secondOperatorStr = MULTIPLY
                firstNumberStr = (50..99).random()
                secondNumberStr = (10..99).random()
                //don't want negative numbers
                if (secondNumberStr> firstNumberStr){
                    secondNumberStr /= 4
                }
                thirdNumberStr = (1..9).random()
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Random numbers are $firstNumberStr and $secondNumberStr and $thirdNumberStr")
                Log.e(LOG_TAG, "Random operators are $firstOperatorStr and $secondOperatorStr")
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsThreeDigits()
            }
            EXTREME -> {
                firstOperatorStr = operationArray[(0..1).random()]
                secondOperatorStr = MULTIPLY
                firstNumberStr = (500..999).random()
                secondNumberStr = (100..999).random()
                //don't want negative numbers
                if (secondNumberStr> firstNumberStr){
                    secondNumberStr /= 4
                }
                thirdNumberStr = (1..9).random()
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Random numbers are $firstNumberStr and $secondNumberStr and $thirdNumberStr")
                Log.e(LOG_TAG, "Random operators are $firstOperatorStr and $secondOperatorStr")
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsThreeDigits()
            }
            ASIAN -> {
                firstOperatorStr = operationArray[(0..1).random()]
                secondOperatorStr = MULTIPLY
                firstNumberStr = (500..999).random()
                secondNumberStr = (100..999).random()
                //don't want negative numbers
                if (secondNumberStr> firstNumberStr){
                    secondNumberStr /= 4
                }
                thirdNumberStr = (10..99).random()
                showAndHideViews(difficulty)
                Log.e(LOG_TAG, "Random numbers are $firstNumberStr and $secondNumberStr and $thirdNumberStr")
                Log.e(LOG_TAG, "Random operators are $firstOperatorStr and $secondOperatorStr")
                Log.e(LOG_TAG, "Step 3: showing strings in text")
                setStringsToTextViewsThreeDigits()
            }

            //TODO: [1]

        }
    }

    private fun setStringsToTextViewsThreeDigits() {
        firstDigit.text = firstNumberStr.toString()
        secondDigit.text = secondNumberStr.toString()
        thirdDigit.text = thirdNumberStr.toString()
        incrementAndDisplayLiveScore(scoreStr)

        //TODO: display correct answer for testing, remove later
        if (firstOperatorStr == ADD && secondOperatorStr == SUBTRACT){
            correctAnswerStr = firstNumberStr + secondNumberStr - thirdNumberStr
        }
        else if (firstOperatorStr == SUBTRACT && secondOperatorStr == ADD){
            correctAnswerStr = firstNumberStr - secondNumberStr + thirdNumberStr
        }
        else if (firstOperatorStr == ADD && secondOperatorStr == MULTIPLY){
            correctAnswerStr = (firstNumberStr + secondNumberStr) * thirdNumberStr
        }
        else if (firstOperatorStr == SUBTRACT && secondOperatorStr == MULTIPLY){
            correctAnswerStr = (firstNumberStr - secondNumberStr) * thirdNumberStr
        }
        testCorrectAnswer.text = "$correctAnswerStr"

        when (firstOperatorStr) {
        ADD -> firstOperator.text = "+"
        SUBTRACT -> firstOperator.text = "-"
        }
        when (secondOperatorStr) {
            ADD -> secondOperator.text = "+"
            SUBTRACT -> secondOperator.text = "-"
            MULTIPLY -> secondOperator.text = "x"
        }
        //TODO: [3]

    }

    //3
    private fun setStringsToTextViewsTwoDigits() {

        firstDigit.text = firstNumberStr.toString()
        secondDigit.text = secondNumberStr.toString()
        incrementAndDisplayLiveScore(scoreStr)
        testCorrectAnswer.text = "${firstNumberStr + secondNumberStr}"

        when (firstOperatorStr) {
            ADD -> firstOperator.text = "+"
        }
    }

    //2-1
    private fun showAndHideViews(difficulty: String) {
        when (difficulty){
            TODDLER -> {
                firstArc.visibility = View.GONE
                secondArc.visibility = View.GONE
                secondOperator.visibility = View.GONE
                thirdDigit.visibility = View.GONE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                Log.e(LOG_TAG, "Correct answers: $numberOfCorrectAnswers")
                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 5 && //5 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()

            }
            EASY -> {
                firstArc.visibility = View.GONE
                secondArc.visibility = View.GONE
                secondOperator.visibility = View.GONE
                thirdDigit.visibility = View.GONE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 10 && //10 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()
            }
            MEDIUM -> {
                firstArc.visibility = View.GONE
                secondArc.visibility = View.GONE
                secondOperator.visibility = View.VISIBLE
                thirdDigit.visibility = View.VISIBLE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 15 && //15 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()
            }
            HARD -> {
                firstArc.visibility = View.VISIBLE
                secondArc.visibility = View.VISIBLE
                secondOperator.visibility = View.VISIBLE
                thirdDigit.visibility = View.VISIBLE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 20 && //20 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()
            }
            EXTREME -> {
                firstArc.visibility = View.VISIBLE
                secondArc.visibility = View.VISIBLE
                secondOperator.visibility = View.VISIBLE
                thirdDigit.visibility = View.VISIBLE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 25 && //25 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()
            }
            ASIAN -> {
                firstArc.visibility = View.VISIBLE
                secondArc.visibility = View.VISIBLE
                secondOperator.visibility = View.VISIBLE
                thirdDigit.visibility = View.VISIBLE
                if (skipCounterStr < 1 || skip.text.toString() == "Skip (0)"){
                    Log.e(LOG_TAG, "Hide skip button")
                    skipCounterStr = 0
                    skip.visibility = View.INVISIBLE
                }

                if (numberOfCorrectAnswers > 0 && //to prevent decreasing on first question
                    (numberOfCorrectAnswers.toFloat() / 5) == (numberOfCorrectAnswers / 5).toFloat() && //can be divided by 5
                    timerMillis > 1000 * 30 && //30 seconds is minimum
                    !lastAnswerWasFalse && lastAnswerWasCorrect //prevent timer going down on wrong answers
                ){
                    timerMillis -= (1000 * 5)
                    canChangeTimer = false
                    amountOfTimesTimerChanged +=1

                    Toast.makeText(this, "Timer decreased by 5 seconds", Toast.LENGTH_SHORT).show()
                    Log.e(LOG_TAG, "Correct answers timer decreased")
                }
                setUpTimer()
            }
            //TODO: [2]

        }
    }

    //2-2
    private fun setUpTimer() {
        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        timer = object :CountDownTimer(timerMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var millisInSeconds: Int = (millisUntilFinished/1000).toInt()
                    timerText.text = "${millisInSeconds} seconds"

                if (millisInSeconds <= 10){
                    timerText.setTextColor(getColor(R.color.red))
                } else if (millisInSeconds > 10){
                    timerText.setTextColor(getColor(R.color.green))
                }
            }

            override fun onFinish() {
                cancel()
                displayWrongAnswer(sharedPref.getString(DIFFICULTY, "-EASY")!!)
            }
        }
        timer.start()
    }

    //4-1
    private fun calculateCorrectAnswerTwoDigits() : Int{
        when (firstOperatorStr){
             ADD -> {
                return firstNumberStr + secondNumberStr
            }
        }
        Log.e(LOG_TAG, "Step 4: Correct answer is ${firstNumberStr + secondNumberStr}")
        return firstNumberStr + secondNumberStr

    }
    //4-2
    private fun calculateCorrectAnswerThreeDigits() : Int{
        var correctAnswer : Int = 0
        Log.e(LOG_TAG, "Step 4: Correct answer is ${firstNumberStr + secondNumberStr - thirdNumberStr}")
        if (firstOperatorStr == ADD && secondOperatorStr == SUBTRACT){
            correctAnswer = firstNumberStr + secondNumberStr - thirdNumberStr
        }
        else if (firstOperatorStr == SUBTRACT && secondOperatorStr == ADD){
            correctAnswer = firstNumberStr - secondNumberStr + thirdNumberStr
        }
        else if (firstOperatorStr == ADD && secondOperatorStr == MULTIPLY){
            correctAnswer= (firstNumberStr + secondNumberStr) * thirdNumberStr
        }
        else if (firstOperatorStr == SUBTRACT && secondOperatorStr == MULTIPLY){
            correctAnswer = (firstNumberStr - secondNumberStr) * thirdNumberStr
        }
        return correctAnswer

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()

    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        Log.e(LOG_TAG, "Timer cancelled")
        timer.cancel()
        super.onDestroy()
    }



}
