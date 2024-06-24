package com.iyam.mysuitmediatest.presentation.secondscreen

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.iyam.mysuitmediatest.model.User

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class SecondScreenViewModel(
    private val extras: Bundle?
) : ViewModel() {
    val user = extras?.getParcelable<User>(SecondScreenActivity.EXTRA_USER)
}
