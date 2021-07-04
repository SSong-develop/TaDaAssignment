package com.ssong_develop.tadaassignment.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssong_develop.tadaassignment.R
import com.ssong_develop.tadaassignment.databinding.FragmentRideEstimationBinding
import com.ssong_develop.tadaassignment.ui.adapter.RideEstimationAdapter
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel
import com.ssong_develop.tadaassignment.utils.AutoClearedBinding
import com.ssong_develop.tadaassignment.utils.hideKeyboard

class RideEstimationFragment : Fragment() {

    private var binding by AutoClearedBinding<FragmentRideEstimationBinding>()

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var rideEstimationAdapter: RideEstimationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRideEstimationBinding.inflate(inflater, container, false)
        .also { FragmentRideEstimationBinding ->
            binding = FragmentRideEstimationBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
        binding.vm = viewModel
        view.hideKeyboard()

        observeErrorHandleData()
        configureRecyclerView()
        observeRideEstimationData()
        observeCouponName()
    }

    private fun onItemClicked(idx: Int) {
        viewModel.setLastSelectedPosition(idx)
    }

    private fun configureRecyclerView() {
        rideEstimationAdapter = RideEstimationAdapter(onItemClicked = { idx ->
            onItemClicked(idx)
        }, viewModel)

        binding.rvRideEstimation.apply {
            adapter = rideEstimationAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), 1))
        }
    }

    private fun observeRideEstimationData() {
        viewModel.rideEstimationData.observe(viewLifecycleOwner) {
            rideEstimationAdapter.submitItem(it)
        }
    }

    private fun observeErrorHandleData() {
        viewModel.errorHandleData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }
    }

    // bindingAdapter로 뺄 수도 있음
    @SuppressLint("SetTextI18n")
    private fun observeCouponName() {
        viewModel.couponName.observe(viewLifecycleOwner) {
            viewModel.fetchRideEstimationData(it)
            binding.tvCouponStatus.apply {
                setTextColor(Color.parseColor("#283873"))
                text = "쿠폰 적용됨\n$it"
            }
        }
    }

    fun navigateRegisterCoupon() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.no_action)
            .replace(R.id.fcv_main, RegisterCouponFragment()).commitNow()
    }
}