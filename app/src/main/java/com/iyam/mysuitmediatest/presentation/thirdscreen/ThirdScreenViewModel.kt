package com.iyam.mysuitmediatest.presentation.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.iyam.mysuitmediatest.data.repository.UserRepository
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class ThirdScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<ResultWrapper<LiveData<PagingData<User>>>>()

    val user: LiveData<ResultWrapper<LiveData<PagingData<User>>>>
        get() = _user

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllUsers().collect {
                _user.postValue(it)
            }
        }
    }
}
