package com.baraka.foodmate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baraka.foodmate.R
import com.baraka.foodmate.models.MealItem
import com.baraka.foodmate.utils.loadImage
import com.baraka.foodmate.views.MealDetailsActivity

class MealListAdapter (var mealContext:Context, val listOfMeals:List<MealItem>):
RecyclerView.Adapter<MealListAdapter.ListViewHolder>()
{
    inner  class  ListViewHolder(var mealItem: View): RecyclerView.ViewHolder(mealItem){
        val mealImg = mealItem.findViewById<ImageView>(R.id.meal_item_image)
        val mealName = mealItem.findViewById<TextView>(R.id.meal_item_title)
        val mealPrice = mealItem.findViewById<TextView>(R.id.meal_item_price)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
      val inflator = LayoutInflater.from(parent.context)
        val mealView = inflator.inflate(R.layout.meal_item_layout, parent, false)
        return ListViewHolder(mealView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
      val currentMealItem  = listOfMeals[position]
        holder.mealName.text =  currentMealItem.name
        holder.mealPrice.text = currentMealItem.price.toString()
        currentMealItem.imageUrl?.let { holder.mealImg.loadImage(it) }
        holder.mealItem.setOnClickListener{
            mealContext.startActivity(Intent(mealContext,MealDetailsActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
      return  listOfMeals.size
    }

}