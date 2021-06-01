package com.example.quizter.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizter.data.Constants
import com.example.quizter.R
import kotlinx.android.synthetic.main.activity_result.*

class FailedResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed_result)

        val correcAns = intent.getIntExtra(Constants.CORRECT_ANSWER, 0)
        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        tv_CorrectAnswer.text = "You got " + correcAns +" out of " + totalQuestion

        tv_finish.setOnClickListener{
            val startActivityIntent = Intent(this, QuizList::class.java)
            startActivity(startActivityIntent)
        }
    }
}