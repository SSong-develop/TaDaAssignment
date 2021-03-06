package com.ssong_develop.tadaassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssong_develop.tadaassignment.R
import com.ssong_develop.tadaassignment.databinding.FragmentRegisterCouponBinding
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel
import com.ssong_develop.tadaassignment.utils.AutoClearedBinding

class RegisterCouponFragment : Fragment() {

    private var binding by AutoClearedBinding<FragmentRegisterCouponBinding>()

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegisterCouponBinding.inflate(layoutInflater, container, false)
        .also { FragmentRegisterCouponBinding ->
            binding = FragmentRegisterCouponBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
    }

    fun navigateBackStack() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, RideEstimationFragment()).commitNow()
    }

    fun registerCoupon() {
        viewModel.setCouponName(binding.edtCouponName.text.toString())
        viewModel.releaseSingleInvoke()
        navigateBackStack()
    }
}