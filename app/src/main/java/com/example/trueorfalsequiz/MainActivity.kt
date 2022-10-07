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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        loadQuestions()
        resumeButton.visibility = View.GONE
        crasher.visibility = View.GONE


        //get the first question, set up the textviews
        questionText.text = quiz.getCurrentQuestion()
        scoreText.text = quiz.setScore()

        //set up the onClickListeners for the buttons
        trueButton.setOnClickListener {
            var correct = quiz.checkAnswer(true)
            if(correct) {
                questionText.text = "Good Job! That fact is true."
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, "\uD83D\uDFE2 Correct!", Toast.LENGTH_SHORT).show()

            }
            else {
                questionText.text = "Nope. That fact isn't true!"
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, "\uD83D\uDD34 Incorrect...", Toast.LENGTH_SHORT).show()

            }

            scoreText.text = quiz.setScore()
        }

        falseButton.setOnClickListener {
            var correct = quiz.checkAnswer(false)
            if(correct) {
                questionText.text = "Good Job! That fact isn't true."
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, "\uD83D\uDFE2 Correct!", Toast.LENGTH_SHORT).show()

            }
            else {
                questionText.text = "Nope. That fact is true!"
                //Button Visibility
                resumeButton.visibility = View.VISIBLE
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                Toast.makeText(this, "\uD83D\uDD34 Incorrect...", Toast.LENGTH_SHORT).show()

            }

            scoreText.text = quiz.setScore()
        }

        resumeButton.setOnClickListener {
            if (buttonThreeValue == 1) {
                questionText.text = quiz.getCurrentQuestion()
                scoreText.text = quiz.setScore()
                Log.d(TAG, "onCreate: ${quiz.current_Question}")
            }
            else if (buttonThreeValue == 2) {
                quiz.resetQuiz()
                buttonThreeValue = 1
                trueButton.visibility = View.VISIBLE
                falseButton.visibility = View.VISIBLE
                resumeButton.visibility = View.GONE
                crasher.visibility = View.GONE
                questionText.text = quiz.getCurrentQuestion()
                scoreText.text = quiz.setScore()
            }

            //Button Visibility
            if(quiz.endingScreen()) {
                resumeButton.text = "RESTART"
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