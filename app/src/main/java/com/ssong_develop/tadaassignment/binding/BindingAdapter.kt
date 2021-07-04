package com.ssong_develop.tadaassignment.binding

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
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
            textView.setTextColor(Color.parseColor("#9DA0AE"))
        }else{
            textView.apply {
                setTextColor(Color.parseColor("#283873"))
                text = "쿠폰적용됨\n$couponName"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("strike_through")
    fun setStrikeThrough(textView : TextView , isStrikeThrough : Boolean){
        if(isStrikeThrough)
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("original_cost_text")
    fun setOriginalCostText(textView : TextView , originalCost : Int){
        if(originalCost == 0){
            textView.text = ""
        }else{
            textView.text = "예상${originalCost}원"
        }
    }
}