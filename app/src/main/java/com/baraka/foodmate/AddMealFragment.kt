package com.baraka.foodmate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_add_meal.*
import java.util.*

class AddMealFragment : Fragment() {
    private val mediaRequestCode: Int = 111
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
        editMealImage.setOnClickListener {
            choosePicture()
        }
        uploadData()

    }

    private fun choosePicture() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), mediaRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mediaRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
            editMealImage.setImageBitmap(bitmap)
        }
    }

    private fun uploadData() {
        add_meal_Button.setOnClickListener {

            val mealName = editMealTitle.text.toString().trim()
            val mealDesc = editMealDescription.text.toString().trim()
            val mealPrice = editPrice.text.toString().trim()

            if (isEmpty(mealName)) {
                editMealTitle.error = "Name for the meal is required"
                editMealTitle.requestFocus()
                return@setOnClickListener
            }
            if (isEmpty(mealPrice)) {
                editPrice.error = "Price for the meal is required"
                editPrice.requestFocus()
                return@setOnClickListener
            }
            if (isEmpty(mealDesc)) {
                editMealDescription.error = "A short description for the meal is required"
                editMealDescription.requestFocus()
                return@setOnClickListener
            }
            val uniqueID = UUID.randomUUID().toString()
            if (filePath != null) {
                uploadProgressBar.visibility = View.VISIBLE
                val imageRef =
                    FirebaseStorage.getInstance().reference.child("meal_images/${uniqueID}")
                imageRef.putFile(filePath)
                    .addOnSuccessListener {
                        uploadProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(context, "Image uploaded", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { exception ->
                        uploadProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(context, exception?.message, Toast.LENGTH_LONG).show()
                    }

                val dataRef =
                    FirebaseDatabase.getInstance().reference.child("meals_data/${uniqueID}")
                dataRef.child("name").setValue(mealName)
                dataRef.child("price").setValue(mealPrice)
                dataRef.child("desc").setValue(mealDesc)
                dataRef.child("imageUrl").setValue("https://firebasestorage.googleapis.com/v0/b/foodlyx-b63f6.appspot.com/o/meal_images%2F${uniqueID}?alt=media&token=e6145dd1-4a84-4c88-a11e-4d825fbbd906")
                startActivity(Intent(context, MainActivity::class.java))
            } else {
                Toast.makeText(context, "Image not selected", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


        }
    }

}