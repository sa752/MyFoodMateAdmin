package com.baraka.foodmate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_meal.*
import java.util.*

class AddMealFragment : Fragment() {
  private val mediaRequestCode:Int = 111
    private lateinit var filePath: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //var uniqueID = UUID.randomUUID().toString()
        add_meal_Button.setOnClickListener {
            getData()
        }
        editMealImage.setOnClickListener{
            choosePicture()
        }

    }

   private  fun choosePicture(){
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), mediaRequestCode )
    }

    private fun getData() {
            val mealName = editMealTitle.text.toString().trim()
            val mealDesc = editMealDescription.text.toString().trim()
            val mealPrice = editPrice.text.toString().trim()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == mediaRequestCode && resultCode == Activity.RESULT_OK && data !=null){
            filePath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,filePath)
            editMealImage.setImageBitmap(bitmap)
        }
    }

}