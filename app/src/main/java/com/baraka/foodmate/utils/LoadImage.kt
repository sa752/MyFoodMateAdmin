package com.baraka.foodmate.utils

import android.widget.ImageView
import com.baraka.foodmate.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/* set image ui*/

fun ImageView.loadImage(url:String){
    val defaultOptions = RequestOptions()
        .placeholder(R.drawable.image_place_holder)
        .error(R.drawable.image_place_holder)
    Glide.with(context).setDefaultRequestOptions(defaultOptions).load(url)
        .into(this)
}