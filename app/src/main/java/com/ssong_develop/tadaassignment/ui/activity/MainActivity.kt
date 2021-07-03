package com.ssong_develop.tadaassignment.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssong_develop.tadaassignment.R
import com.ssong_develop.tadaassignment.databinding.ActivityMainBinding
import com.ssong_develop.tadaassignment.di.Injection
import com.ssong_develop.tadaassignment.domain.RideEstimation
import com.ssong_develop.tadaassignment.ui.adapter.RideEstimationAdapter
import com.ssong_develop.tadaassignment.ui.fragment.RideEstimationFragment
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        Injection.provideMainViewModelFactory()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bindViewModel = viewModel
        binding.lifecycleOwner = this

        supportFragmentManager.beginTransaction().replace(R.id.fcv_main,RideEstimationFragment()).commitNow()
    }


}