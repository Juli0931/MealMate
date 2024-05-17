package com.example.mealmate.view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ImageUtil(){

    private val movieOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
       // .placeholder(R.drawable.ic_launcher_foreground)
       // .error(R.drawable.ic_launcher_foreground)

    fun renderImageCenterCrop(context: Context, imageUrl:String?, view:ImageView ) {
        Glide
            .with(context)
            .load(imageUrl)
            .apply(movieOptions)
            .into(view)
    }

    fun renderImageCenterCrop(context: Context, imageUrl:Uri?, view:ImageView ) {
        Glide
            .with(context)
            .load(imageUrl)
            .apply(movieOptions)
            .into(view)
    }

    //FROM: https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
    fun drawableToBitmap(drawable: Drawable): Bitmap {

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}