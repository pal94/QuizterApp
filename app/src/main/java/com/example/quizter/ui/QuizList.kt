package com.example.quizter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizter.data.Constants
import com.example.quizter.R
import com.example.quizter.data.Category
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_quiz_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class QuizList : AppCompatActivity(), OnItemClickListener {
    //private lateinit var linearLayoutManager: LinearLayoutManager
    private var mCategoryList: List<Category>? =null
    private var mCategoryName: ArrayList<String>?=null
    private var len=0;
    //private var itemAdapter = ItemApdapter(this, mCategoryList!!);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list)

        doAsync {
            val url:String = URL("https://opentdb.com/api_category.php").readText()

            uiThread {
                Log.d("JSONDATA", "json $url")
                val resultObject = Gson().fromJson(url, JsonObject::class.java)
                val json = resultObject.get("trivia_categories");
                Log.d("JSONARRAY", ""+json)
                mCategoryList = Gson().fromJson(json, Array<Category>::class.java).toList()
                len = mCategoryList!!.size
                setView();
                Log.d("SIZE", ""+len)
            }

        }

        //linearLayoutManager = LinearLayoutManager(this)
    }
    private fun setView(){
        recycler_view_items.layoutManager = LinearLayoutManager(this)

        val itemAdapter = ItemApdapter(this, mCategoryList!!)

        recycler_view_items.adapter = itemAdapter
    }
    private fun getCategoryList(length:Int): ArrayList<String> {
        val list = ArrayList<String>()
        for(i in 0..length-1){
            val category = mCategoryList!![i]
            Log.d("NAME", ""+category)
            val name = category.name
            Log.d("NAME", ""+name)
            list.add(name)
        }

        return list
    }

    override fun onItemClicked(position: Int) {
        val clickedItem: Category = mCategoryList!![position]
        val categoryIntent = Intent(this, GeneralKnowledgeActivity::class.java)
        categoryIntent.putExtra(Constants.CATEGORY_NAME, clickedItem.name)
        categoryIntent.putExtra(Constants.CATEGORY_ID, clickedItem.id)
        startActivity(categoryIntent)
        //clickedItem.name="Clicked"
        //itemAdapter.notifyItemChanged(position)
    }
}