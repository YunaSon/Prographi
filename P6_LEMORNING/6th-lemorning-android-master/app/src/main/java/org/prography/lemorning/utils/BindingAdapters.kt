package org.prography.lemorning.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import org.prography.lemorning.R
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("bind_img_url_circle")
    @JvmStatic
    fun bindImgUrlCircle(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.shape_circle_gray)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(view)
        view.clipToOutline = true
    }

    @BindingAdapter("bind_img_url_round_8")
    @JvmStatic
    fun bindImgUrlRound8(view : ImageView, url : String?) {
        view.clipToOutline = true
        view.setBackgroundResource(R.drawable.shape_8_round_gradient_top0000_bottom1000)
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.img_foryou_sample1)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("bind_img_url")
    @JvmStatic
    fun bindImgUrl(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_lemorning)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(view)
    }


    @BindingAdapter("bind_img_url_opaque")
    @JvmStatic
    fun bindImgUrlOpaque(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(BlurTransformation(30, 3), CenterCrop())
            .error(R.drawable.shape_circle_gray)
            .into(view)
        view.clipToOutline = true
    }

    @BindingAdapter("bind_profile_uri")
    @JvmStatic
    fun bindImgUri(view: ImageView, uri: Uri?) {
        Glide.with(view.context)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .error(R.drawable.ic_profile)
            .into(view)
        view.clipToOutline = true
    }

    @BindingAdapter("time")
    @JvmStatic
    fun timeToDay(view : TextView, time : Long) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.US)
        view.text = sdf.format(Date(time))
    }

    @BindingAdapter("m_ss")
    @JvmStatic
    fun intToM_ss(view : TextView, time : Int) {
        val sdf = SimpleDateFormat("mm:ss", Locale.US)
        view.text = sdf.format(Date(time.toLong()))
    }
}