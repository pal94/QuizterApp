package com.example.quizter

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*

class QuizQuestion : AppCompatActivity(), View.OnClickListener{

    private var mCurrentPosition:Int = 1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectedOptionPos:Int = 0
    private var mCorrectAnswers :Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mQuestionList = Constants.getQuestions()

        setQuestion()

        tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        submit.setOnClickListener(this)

    }

    private fun setQuestion(){

        //mCurrentPosition=1
        val question = mQuestionList!![mCurrentPosition-1]

        defaultOptions()

        if(mCurrentPosition==mQuestionList!!.size){
            submit.text="FINISH"
        }
        else{
            submit.text="SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max
        tv_question.text=question!!.question
        iv_flag.setImageResource(question.image)
        tv_option1.text=question.optionOne
        tv_option2.text=question.optionTwo
        tv_option3.text=question.optionThree
        tv_option4.text=question.optionFour
    }

    private fun defaultOptions(){
        val options = ArrayList<TextView>()
        options.add(0, findViewById(R.id.tv_option1))
        options.add(1, findViewById(R.id.tv_option2))
        options.add(2, findViewById(R.id.tv_option3))
        options.add(3, findViewById(R.id.tv_option4))

        for(option in options){
            option.setTextColor((Color.parseColor("#7A8089")))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)

        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option1 -> {
                selectedOption(tv_option1, 1)
            }
            R.id.tv_option2->{
                selectedOption(tv_option2,2)
            }
            R.id.tv_option3->{
                selectedOption(tv_option3,3)
            }
            R.id.tv_option4->{
                selectedOption(tv_option4,4)
            }
            R.id.submit->{
                if(mSelectedOptionPos==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition<= mQuestionList!!.size->{
                            setQuestion()
                        }
                        else->{
                            if(mCorrectAnswers>=3){
                                val intent = Intent(this,ResultActivity::class.java)
                                intent.putExtra(Constants.CORRECT_ANSWER, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                                startActivity(intent)
                            }else
                            {
                                val intent = Intent(this,FailedResult::class.java)
                                intent.putExtra(Constants.CORRECT_ANSWER, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                                startActivity(intent)
                            }

                        }
                    }
                }
                else{
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer != mSelectedOptionPos){
                        answerView(mSelectedOptionPos, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition==mQuestionList!!.size){
                        submit.text="FINISH"
                    }else{
                        submit.text="GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPos=0
                }
            }
        }
    }

    private fun answerView(answer:Int, drawableView:Int){
        when(answer){
            1->{
                tv_option1.background=ContextCompat.getDrawable(this, drawableView)
            }
            2->{
                tv_option2.background=ContextCompat.getDrawable(this, drawableView)
            }
            3->{
                tv_option3.background=ContextCompat.getDrawable(this, drawableView)
            }
            4->{
                tv_option4.background=ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOption(tv:TextView, selectedOptionNumber:Int){
        defaultOptions()
        mSelectedOptionPos = selectedOptionNumber

        tv.setTextColor((Color.parseColor("#7A8089")))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }


}