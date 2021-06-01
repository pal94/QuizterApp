package com.example.quizter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizter.R
import com.example.quizter.data.Category
import kotlinx.android.synthetic.main.quiz_category_row.view.*

class ItemApdapter(val itemClickListner: OnItemClickListener, var items: List<Category>): RecyclerView.Adapter<ItemApdapter.ViewHolder>() {
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{
        val tvItem = view.tv_item_name
        val cardViewItem=view.card_view_item

        init{
            view.setOnClickListener(this)
        }

//        fun bind(item: Category, clickListener: OnItemClickListener){
//            tvItem.text = item.name
//            clickListener.onItemClicked(item)
//        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                itemClickListner.onItemClicked(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.quiz_category_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.tvItem.text = item.name

        //holder.bind(item, itemClickListner)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface OnItemClickListener{
    fun onItemClicked(position: Int)
}