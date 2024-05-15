package tech.benhack.ui.helpers

import android.content.Context
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
}