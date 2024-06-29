package com.iyam.mysuitmediatest.presentation.thirdscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyam.mysuitmediatest.R
import com.iyam.mysuitmediatest.databinding.ActivityThirdScreenBinding
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.presentation.secondscreen.SecondScreenActivity
import com.iyam.mysuitmediatest.presentation.thirdscreen.user.LoaderAdapter
import com.iyam.mysuitmediatest.presentation.thirdscreen.user.UserAdapter
import com.iyam.mysuitmediatest.utils.proceedWhen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        getUsers()
        setUserRV()
    }

    private fun getUsers() {
        thirdScreenViewModel.getUsers()
        lifecycleScope.launch {
            thirdScreenViewModel.user.observe(this@ThirdScreenActivity) {
                it.proceedWhen(
                    doOnLoading = {
                        binding.pbUserLoader.isVisible = true
                        binding.rvUser.isVisible = false
                    },
                    doOnSuccess = {
                        binding.pbUserLoader.isVisible = false
                        binding.rvUser.isVisible = true
                        it.payload?.observe(this@ThirdScreenActivity) {
                            userAdapter.submitData(lifecycle, it)
                        }
                    }
                )
            }
        }
    }

    private fun setUserRV() {
        lifecycleScope.launch {
            delay(2000)
            binding.rvUser.apply {
                adapter = userAdapter.withLoadStateFooter(LoaderAdapter())
                layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
                setHasFixedSize(true)
                var isToastShown = false
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val totalItemCount = layoutManager.itemCount
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                        if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == totalItemCount - 1) {
                            if (userAdapter.itemCount == totalItemCount && !isToastShown) {
                                Toast.makeText(this@ThirdScreenActivity, getString(R.string.no_more_pages), Toast.LENGTH_SHORT).show()
                                isToastShown = true
                            }
                        }
                    }
                })
            }
        }
    }

    private fun setOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
