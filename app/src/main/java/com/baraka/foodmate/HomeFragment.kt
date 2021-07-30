package com.baraka.foodmate

import android.os.Bundle
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
    private  var databaseListener:  ValueEventListener? = null
    private  lateinit var listOfMeals: MutableList<MealItem>
    private lateinit var mealAdapater: MealListAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOfMeals = arrayListOf()
        getData()
        home_page_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            mealAdapater = MealListAdapter(context, listOfMeals )
            adapter = mealAdapater
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        databaseReference?.removeEventListener(databaseListener!!)
    }

 private  fun getData(){
       databaseReference = FirebaseDatabase.getInstance().getReference("meals_data")
       databaseReference!!.addValueEventListener(object : ValueEventListener {

           override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   for ( mealItem in snapshot.children){
                       val mealItemData = mealItem.getValue(MealItem::class.java)
                       mealItemData!!.key = mealItem.key
                       listOfMeals.add(mealItemData)
                   }
                   //unsure
                   home_page_recycler_view.adapter = MealListAdapter(requireContext(),listOfMeals )
               }

           }

           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
           }

       })
    }

}
