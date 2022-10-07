package com.example.trueorfalsequiz

class Quiz(val questions: List<Question>) {
    //variables to track score and current question
    var score = 0
    var current_Question = -1

    //returns question and final scores; checks if there is another question
    fun getCurrentQuestion() : String {
        if(current_Question != 19) {
            current_Question++
            return questions[current_Question].question
        }
        else {
            current_Question++
            if (score == 0)
                return "FINAL SCORE: " + score + "/20 points! Ouch. Try again?"
            else if (score >= 1 && score <= 9)
                return "FINAL SCORE: " + score + "/20 points! You can do better than that!"
            else if (score >= 10 && score <= 19)
                return "FINAL SCORE: " + score + "/20 points! Well done!"
            return "FINAL SCORE: " + score + "/20 points! You got them all right!"
        }
    }

    //checks if answer is correct
    fun checkAnswer(selectedAnswer: Boolean): Boolean {
        if (questions[current_Question].answer == selectedAnswer) {
            score++
            return true
        }
        else {
            return false
        }
    }

    //updates the score visually
    fun setScore(): String {
        if (current_Question != 20)
            return "SCORE: " + score + "/20 points!"
        else {
            return "Want to try again?"
        }
    }

    fun endingScreen(): Boolean {
        if (current_Question == 20)
            return true
        else
            return false
    }

    fun resetQuiz() {
        score = 0
        current_Question = -1
    }


    //give the final score, reset the quiz?, shuffle questions?
}