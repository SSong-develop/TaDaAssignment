package com.ssong_develop.tadaassignment.binding

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssong_develop.tadaassignment.domain.RideStatus
import com.ssong_develop.tadaassignment.utils.pixelToDp

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["image_url", "image_width", "image_height"])
    fun setImage(imageView: ImageView, imageUrl: String, imageWidth: Int, imageHeight: Int) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .override(
                imageView.context.pixelToDp(imageWidth),
                imageView.context.pixelToDp(imageHeight)
            )
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("ride_status_visibility")
    fun setRideStatusVisibility(textView: TextView, rideStatus: RideStatus?) {
        if (rideStatus != null)
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("coupon_status")
    fun setCouponStatus(textView : TextView , couponName : String?){
        if(couponName == null){
            textView.text = "보유한 쿠폰 없음"
        }else{
            textView.apply {
                setTextColor(Color.parseColor("#283873"))
                text = "쿠폰적용됨\n$couponName"
            }
        }
    }
}