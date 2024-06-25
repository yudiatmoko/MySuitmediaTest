package com.iyam.mysuitmediatest.presentation.thirdscreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var page = 1
    private var perPage = 10
    private var totalPage = 2
    private lateinit var layoutManager: LinearLayoutManager

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
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        parameters["per_page"] = perPage.toString()
        thirdScreenViewModel.getUsers(parameters)
        thirdScreenViewModel.user.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { userAdapter.setData(it) }
                }
            )
        }
    }

    private fun setUserRV() {
        binding.rvUser.apply {
            adapter = userAdapter
            setHasFixedSize(true)
        }
        layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                val visibleItemCount = binding.rvUser.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = userAdapter.itemCount
                if (page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getUsers()
                    }
                }
                super.onScrolled(
                    recyclerView,
                    dx,
                    dy
                )
            }
        })
    }

    private fun setOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
