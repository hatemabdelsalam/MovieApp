package com.etax.movieapp.core.presentation.util


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.etax.movieapp.R
import com.etax.movieapp.network.Urls

class ImageUtil {
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadPicture(imageView: ImageView, url: String?) {
            Glide.with(imageView.context).load(Urls.IMAGE_BASE_URL + url)
                .placeholder(R.drawable.placeholder_img).into(imageView)
        }

    }

}