package com.iyam.mysuitmediatest.presentation.thirdscreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iyam.mysuitmediatest.R
import com.iyam.mysuitmediatest.databinding.ActivityThirdScreenBinding
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.presentation.secondscreen.SecondScreenActivity
import com.iyam.mysuitmediatest.presentation.thirdscreen.user.UserAdapter
import com.iyam.mysuitmediatest.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThirdScreenActivity : AppCompatActivity() {

    private val binding: ActivityThirdScreenBinding by lazy {
        ActivityThirdScreenBinding.inflate(layoutInflater)
    }
    private val userAdapter: UserAdapter by lazy {
        UserAdapter { user: User ->
            navigateToSecondScreen(user)
        }
    }

    private fun navigateToSecondScreen(user: User) {
        SecondScreenActivity.startActivityFromThirdScreen(this, user)
    }

    private val thirdScreenViewModel: ThirdScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        setOnClickListener()
        setUserRV()
    }

    private fun setUserRV() {
        thirdScreenViewModel.getUsers()
        thirdScreenViewModel.user.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { userAdapter.setData(it) }
                }
            )
        }
        binding.rvUser.apply {
            adapter = userAdapter
        }
    }

    private fun setOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
