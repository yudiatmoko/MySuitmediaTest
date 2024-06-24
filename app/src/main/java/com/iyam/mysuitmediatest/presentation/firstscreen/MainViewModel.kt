package com.iyam.mysuitmediatest.presentation.firstscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iyam.mysuitmediatest.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class MainViewModel(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    val userNameLiveData = userPreferenceDataSource.getUserNameFlow().asLiveData(Dispatchers.IO)

    fun setUserName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.setUserName(name)
        }
    }
}
