package com.iyam.mysuitmediatest.presentation.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _user = MutableLiveData<ResultWrapper<List<User>>>()

    val user: LiveData<ResultWrapper<List<User>>>
        get() = _user

    fun getUsers(parameters: HashMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers(parameters).collect {
                _user.postValue(it)
            }
        }
    }
}
