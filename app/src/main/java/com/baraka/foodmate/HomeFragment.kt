package com.baraka.foodmate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.baraka.foodmate.adapters.MealListAdapter
import com.baraka.foodmate.models.MealItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    //initialize firebase
    private var databaseReference:DatabaseReference? = null
    private  lateinit var listOfMeals: ArrayList<MealItem>
    private lateinit var mealAdapter: MealListAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_page_recycler_view.layoutManager = LinearLayoutManager(context)
        home_page_recycler_view.setHasFixedSize(true)
        listOfMeals = MealDataSource.createDataSet()
        mealAdapter = MealListAdapter(requireContext(), listOfMeals)
        home_page_recycler_view.adapter  = mealAdapter
    }


 private  fun getData(){

       databaseReference = FirebaseDatabase.getInstance().getReference("meal_data")
       databaseReference!!.addValueEventListener(object : ValueEventListener {

           override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   for ( mealSnapShot in snapshot.children){
                       val mealItemData = mealSnapShot.getValue(MealItem::class.java)
//                       mealItemData!!.key = mealSnapShot.key
                       listOfMeals.add(mealItemData!!)

                   }

                   mealAdapter = MealListAdapter(requireContext(), listOfMeals)
                   home_page_recycler_view.adapter  = mealAdapter
               }

           }

           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
           }

       })
    }

}
