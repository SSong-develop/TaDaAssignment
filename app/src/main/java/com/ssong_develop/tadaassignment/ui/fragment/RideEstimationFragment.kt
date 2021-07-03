package com.ssong_develop.tadaassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssong_develop.tadaassignment.R
import com.ssong_develop.tadaassignment.databinding.FragmentRideEstimationBinding
import com.ssong_develop.tadaassignment.ui.adapter.RideEstimationAdapter
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel
import com.ssong_develop.tadaassignment.utils.AutoClearedBinding

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

    private fun observeCouponName() {
        viewModel.couponName.observe(viewLifecycleOwner) {
            viewModel.fetchRideEstimationData(it)
        }
    }

    fun navigateRegisterCoupon() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.no_action)
            .replace(R.id.fcv_main, RegisterCouponFragment()).commitNow()
    }
}