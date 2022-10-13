package com.example.trueorfalsequiz

class Quiz(val questions: List<Question>) {
    //variables to track score and current question
    var score = 0
    var current_Question = -1
    val SCORE_LOW = 0
    val SCORE_MEDIUM = 1
    val SCORE_HIGH = 2
    val PERFECT = 3

    fun getFinalScoreRange() : Int {
        if(score == 0) return SCORE_LOW
        else if(score >= 1 && score <= 9) return SCORE_MEDIUM
        else if(score >= 10 && score <= 19) return SCORE_HIGH
        else return PERFECT
    }

    // return false if there are no more questions
    fun advanceToNextQuestions() : Boolean {
        current_Question++
        return current_Question <= 19
    }

    //returns question and final scores; checks if there is another question
    fun getCurrentQuestion() : String {
        return questions[current_Question].question
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

    //updates the score visually--this doesn't seem to set the score. it looks like it checks if
    // there are  more questions?  so maybe change the name of this to reflect that ok
    fun scoreDisplay(): Boolean {
        if (current_Question != 20)
            return true
        else {
            return false
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