package com.example.github.igenius.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.igenius.R
import com.example.github.igenius.githubrepositories.repositorieslist.RepositoriesListAdapter
import com.example.github.igenius.githubrepositories.repositorieslist.RepositoryDataItem


object BindingAdapters {

    /**
     * Use binding adapter to set the recycler view data using livedata object
     */
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("android:liveData")
    @JvmStatic
    fun setRecyclerViewData(recyclerView: RecyclerView, items: List<RepositoryDataItem>?) {
        val adapter = recyclerView.adapter as RepositoriesListAdapter
        adapter.submitList(items)
    }

    /**
     * Use this binding adapter to show and hide the views using boolean variables
     */
    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE)
                    view.fadeIn()
            } else {
                if (view.visibility == View.VISIBLE)
                    view.fadeOut()
            }
        }
    }

    /**
     * Use binding adapter to set the tint color
     */
    @BindingAdapter("android:tintColor")
    @JvmStatic
    fun setTintColor(imageView: ImageView, color: Int) {
        imageView.setColorFilter(color)
    }

    /**
     * Use binding adapter to set the visibility
     */
    @BindingAdapter("android:itemVisible")
    @JvmStatic
    fun setVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}


@BindingAdapter("profileImage")
fun fetchImage(imgView: ImageView, src: String?) {
    src?.let {
        val imgUri = src.toUri().buildUpon().scheme("https").build()
        //Add Glide call to load image use ic_beer_mug as a placeholder and for errors.
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_profile)
            )
            .into(imgView)
    }
}