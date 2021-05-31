package com.example.quizter

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizter.data.Question
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_general_knowledge.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL


class GeneralKnowledgeActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int = 1
    private var mQuestionList: List<Question>? = null
    private var mJumbledList: MutableList<String>? = null
    private var mDisplayOptionList: MutableList<String>? = null
    private var mSelectedOptionPos:Int = 0
    private var mCorrectAnswers :Int = 0

    fun addOptions(correct_answers:String, incorrectAns:ArrayList<String>): MutableList<String>{
        val optionList:MutableList<String> = ArrayList()

        optionList.add(correct_answers)
        optionList.add(incorrectAns.get(0))
        optionList.add(incorrectAns.get(1))
        optionList.add(incorrectAns.get(2))
        return optionList
    }

    private fun setOptions(listOptions:MutableList<String>):MutableList<String>{
        listOptions.shuffle()

        return listOptions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_knowledge)

        //val queue = Volley.newRequestQueue(this)

        val categroyName = intent.getStringExtra(Constants.CATEGORY_NAME);
        val categoryId = intent.getIntExtra(Constants.CATEGORY_ID, 0)

        doAsync {
            val url:String =URL("https://opentdb.com/api.php?amount=10&category="+categoryId+"&type=multiple").readText()

            uiThread {
                Log.d("JSONDATA", "json $url")
                val resultObject = Gson().fromJson(url, JsonObject::class.java)
                val json = resultObject.get("results");
                Log.d("JSONARRAY", ""+json)
                mQuestionList = Gson().fromJson(json, Array<Question>::class.java).toList()
                setQuestions()
            }

        }
        tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        submit.setOnClickListener(this)
    }

   private fun setQuestions(){
        val question=mQuestionList!![mCurrentPosition-1]

        defaultOptions()

        tv_question.text=question.question
       progressBar.progress = mCurrentPosition
       tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max
        Log.d("OPTIONS1", "option1"+question.correct_answer)
        mJumbledList = addOptions(question.correct_answer, question.incorrect_answers)
       Log.d("JumbleList", ""+mJumbledList)
        mDisplayOptionList = setOptions(mJumbledList!!)
       Log.d("JumbleList", ""+mDisplayOptionList)
       tv_option1.text=mDisplayOptionList!![0]
       tv_option2.text=mDisplayOptionList!![1]
       tv_option3.text=mDisplayOptionList!![2]
       tv_option4.text=mDisplayOptionList!![3]
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
            option.background= ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
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
                    Log.d("CURPOS", ""+mCurrentPosition)
                    Log.d("SIZE", ""+mQuestionList!!.size)
                    when{
                        mCurrentPosition<= mQuestionList!!.size->{
                            setQuestions()
                        }
                        else->{
                            if(mCorrectAnswers>=5){
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
                    val mCorrectAnsPos:Int = mDisplayOptionList!!.indexOf(question!!.correct_answer)+1
                    if(mCorrectAnsPos!=mSelectedOptionPos){
                        answerView(mSelectedOptionPos, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(mCorrectAnsPos, R.drawable.correct_option_border_bg)

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


