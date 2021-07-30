package com.baraka.foodmate.models

import com.google.firebase.database.Exclude

data class MealItem(
    var name:String? = null,
    var imageUrl:String? = null,
    var desc:String? = null,
    var price:Int? = null,
    var key:String?= null,
//    @get: Exclude
//     @set: Exclude
)
