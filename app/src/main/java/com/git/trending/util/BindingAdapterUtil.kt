package com.git.trending.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**Created by Shiv Jalkote on 10-May-2021. **/

class BindingAdapterUtil {

    companion object {

        @JvmStatic
        @BindingAdapter("android:loadImage")
        fun loadImage(imageView: ImageView, url: String?) {
            Glide.with(imageView).load(url).into(imageView)
        }

        @JvmStatic
        @BindingAdapter("android:viewVisibility")
        fun viewVisibility(view: View, visibility: Boolean?) {
            view.visibility = if (visibility != null && visibility) View.VISIBLE else View.GONE
        }
    }
}