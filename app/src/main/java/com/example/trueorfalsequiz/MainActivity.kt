package com.example.trueorfalsequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var trueButton: Button
    lateinit var falseButton: Button
    lateinit var resumeButton: Button
    lateinit var questionText: TextView
    lateinit var scoreText: TextView
    lateinit var crasher: Button
    companion object{
        val TAG = "MainActivity"
    }
    private lateinit var quiz: Quiz
    var buttonThreeValue = 1
    var finalScoreRange = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        loadQuestions()
        resumeButton.visibility = View.GONE
        crasher.visibility = View.GONE


        //get the first question, set up the textviews
        if (quiz.advanceToNextQuestions())
            questionText.text = quiz.getCurrentQuestion()
        else {
            finalScoreRange = quiz.getFinalScoreRange()
            if (finalScoreRange == 0)
                questionText.text = getString(R.string.main_badScore, quiz.score)
            if (finalScoreRange == 1)
                questionText.text = getString(R.string.main_decentScore, quiz.score)
            if (finalScoreRange == 2)
                questionText.text = getString(R.string.main_goodScore, quiz.score)
            if (finalScoreRange == 3)
                questionText.text = getString(R.string.main_perfectScore, quiz.score)
        }

        if (quiz.scoreDisplay())
            scoreText.text = getString(R.string.main_score_display, quiz.score, 20)
        else
            scoreText.text = getString(R.string.main_end_message)

        //set up the onClickListeners for the buttons
        trueButton.setOnClickListener {
            var correct = quiz.checkAnswer(true)
            if(correct) {
                questionText.text = getString(R.string.main_correctTrueMsg)
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, getString(R.string.toast_correct), Toast.LENGTH_SHORT).show()

            }
            else {
                questionText.text = getString(R.string.main_wrongTrueMsg)
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, getString(R.string.toast_incorrect), Toast.LENGTH_SHORT).show()

            }

            if (quiz.scoreDisplay())
                scoreText.text = getString(R.string.main_score_display, quiz.score, 20)
            else
                scoreText.text = getString(R.string.main_end_message)
        }

        falseButton.setOnClickListener {
            var correct = quiz.checkAnswer(false)
            if(correct) {
                questionText.text = getString(R.string.main_correctFalseMsg)
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, getString(R.string.toast_correct), Toast.LENGTH_SHORT).show()

            }
            else {
                questionText.text = getString(R.string.main_wrongFalseMsg)
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, getString(R.string.toast_incorrect), Toast.LENGTH_SHORT).show()

            }

            if (quiz.scoreDisplay())
                scoreText.text = getString(R.string.main_score_display, quiz.score, 20)
            else
                scoreText.text = getString(R.string.main_end_message)


        }

        resumeButton.setOnClickListener {
            if (buttonThreeValue == 1) {
                if (quiz.advanceToNextQuestions())
                    questionText.text = quiz.getCurrentQuestion()
                else {
                    finalScoreRange = quiz.getFinalScoreRange()
                    if (finalScoreRange == 0)
                        questionText.text = getString(R.string.main_badScore, quiz.score)
                    if (finalScoreRange == 1)
                        questionText.text = getString(R.string.main_decentScore, quiz.score)
                    if (finalScoreRange == 2)
                        questionText.text = getString(R.string.main_goodScore, quiz.score)
                    if (finalScoreRange == 3)
                        questionText.text = getString(R.string.main_perfectScore, quiz.score)
                }

                if (quiz.scoreDisplay())
                    scoreText.text = getString(R.string.main_score_display, quiz.score, 20)
                else
                    scoreText.text = getString(R.string.main_end_message)
                Log.d(TAG, "onCreate: ${quiz.current_Question}")
            }
            else if (buttonThreeValue == 2) {
                quiz.resetQuiz()
                buttonThreeValue = 1
                trueButton.visibility = View.VISIBLE
                falseButton.visibility = View.VISIBLE
                resumeButton.visibility = View.GONE
                crasher.visibility = View.GONE

                if (quiz.advanceToNextQuestions())
                    questionText.text = quiz.getCurrentQuestion()
                else {
                    finalScoreRange = quiz.getFinalScoreRange()
                    if (finalScoreRange == 0)
                        questionText.text = getString(R.string.main_badScore, quiz.score)
                    if (finalScoreRange == 1)
                        questionText.text = getString(R.string.main_decentScore, quiz.score)
                    if (finalScoreRange == 2)
                        questionText.text = getString(R.string.main_goodScore, quiz.score)
                    if (finalScoreRange == 3)
                        questionText.text = getString(R.string.main_perfectScore, quiz.score)
                }
                if (quiz.scoreDisplay())
                    scoreText.text = getString(R.string.main_score_display, quiz.score, 20)
                else
                    scoreText.text = getString(R.string.main_end_message)
            }

            //Button Visibility
            if(quiz.endingScreen()) {
                resumeButton.text = getString(R.string.main_restartText)
                buttonThreeValue = 2
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                crasher.visibility = View.VISIBLE
            }
            else {
                resumeButton.visibility = View.GONE
                trueButton.visibility = View.VISIBLE
                falseButton.visibility = View.VISIBLE
            }
        }

        crasher.setOnClickListener {
            finish()
        }


    }

    private fun loadQuestions() {
        //Loading questions from JSON
        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        //file successfully read
        Log.d(TAG, "onCreate: $jsonString")

        //convert to a list of Question objects using GSON
        val gson = Gson()

        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonString, type)
        Log.d(TAG, "onCreate: $questions")
        //create quiz object from list of questions
        quiz = Quiz(questions)
    }

    private fun wireWidgets() {
        trueButton = findViewById(R.id.button_true)
        falseButton = findViewById(R.id.button_false)
        questionText = findViewById(R.id.question_text)
        resumeButton = findViewById(R.id.next_question)
        scoreText = findViewById(R.id.score_text)
        crasher = findViewById(R.id.crasher)
    }
}