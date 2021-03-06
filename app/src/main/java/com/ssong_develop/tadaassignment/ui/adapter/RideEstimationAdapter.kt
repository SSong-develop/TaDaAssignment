package com.ssong_develop.tadaassignment.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.tadaassignment.databinding.ViewRideEstimateBinding
import com.ssong_develop.tadaassignment.domain.RideEstimation
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel
import com.ssong_develop.tadaassignment.utils.rollbackOriginSize
import com.ssong_develop.tadaassignment.utils.sizeUpImageView

class RideEstimationAdapter(
    private val onItemClicked: (idx: Int) -> Unit,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RideEstimationAdapter.RideEstimationViewHolder>() {

    private val itemList = mutableListOf<RideEstimation>()

    private val lastSelectedPosition
        get() = viewModel.lastSelectedPosition.value ?: -1

    inner class RideEstimationViewHolder(private val binding: ViewRideEstimateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.onClick = {
                notifyItemRangeChanged(0, itemList.size)
                onItemClicked(adapterPosition)
            }
        }

        fun bind(item: RideEstimation) {
            binding.item = item
            if (lastSelectedPosition == adapterPosition) {
                binding.ivRideEstimateCar.sizeUpImageView(
                    item.rideType.rideTypeImageUrl.rideTypeImageWidth,
                    item.rideType.rideTypeImageUrl.rideTypeImageHeight
                )
                binding.clItem.setBackgroundColor(Color.parseColor("#3332A2FA"))
            } else {
                binding.ivRideEstimateCar.rollbackOriginSize(
                    item.rideType.rideTypeImageUrl.rideTypeImageWidth,
                    item.rideType.rideTypeImageUrl.rideTypeImageHeight
                )
                binding.clItem.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideEstimationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewRideEstimateBinding.inflate(layoutInflater, parent, false)
        return RideEstimationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideEstimationViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun submitItem(list: List<RideEstimation>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }
}