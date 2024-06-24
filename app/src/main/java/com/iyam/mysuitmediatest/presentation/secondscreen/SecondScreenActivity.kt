package com.iyam.mysuitmediatest.presentation.secondscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iyam.mysuitmediatest.R
import com.iyam.mysuitmediatest.databinding.ActivitySecondScreenBinding
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.presentation.firstscreen.MainViewModel
import com.iyam.mysuitmediatest.presentation.thirdscreen.ThirdScreenActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SecondScreenActivity : AppCompatActivity() {

    private val binding: ActivitySecondScreenBinding by lazy {
        ActivitySecondScreenBinding.inflate(layoutInflater)
    }

    private val viewModel: SecondScreenViewModel by viewModel { parametersOf(intent?.extras) }
    private val mainViewModel: MainViewModel by viewModel()

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
        setClickListener()
        setGreetingName()
        setSelectedName(viewModel.user)
    }

    private fun setSelectedName(user: User?) {
        user?.let {
            val name = listOf(user.firstName, user.lastName)
            val fullName = name.joinToString(" ")
            binding.tvSelectedName.text = fullName
        }
    }

    private fun setGreetingName() {
//        val username = intent.getStringExtra(EXTRA_NAME)
        mainViewModel.userNameLiveData.observe(this) {
            binding.tvUserName.text = it
        }
    }

    private fun setClickListener() {
        binding.btnChoose.setOnClickListener {
            navigateToThirdScreen()
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun navigateToThirdScreen() {
        val intent = Intent(this, ThirdScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_USER = "EXTRA_USER"
        fun startActivity(context: Context, name: String) {
            val intent = Intent(context, SecondScreenActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            intent.putExtra(EXTRA_NAME, name)
            context.startActivity(intent)
        } fun startActivityFromThirdScreen(context: Context, user: User) {
            val intent = Intent(context, SecondScreenActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            intent.putExtra(EXTRA_USER, user)
            context.startActivity(intent)
        }
    }
}
