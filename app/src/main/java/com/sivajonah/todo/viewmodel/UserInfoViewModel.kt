package com.sivajonah.todo.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sivajonah.todo.model.UserInfo;
import com.sivajonah.todo.repository.UserInfoRepository;
import com.sivajonah.todo.repository.UserInfoRepositoryInterface
import kotlinx.coroutines.launch

import okhttp3.MultipartBody;

class UserInfoViewModel(private val userInfoRepository : UserInfoRepositoryInterface = UserInfoRepository()): ViewModel() {
    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    fun getInfo() {
        viewModelScope.launch {
            userInfoRepository.getInfo()?.let { newUserInfo ->
                _userInfo.value = newUserInfo
            }
        }
    }

    fun updateAvatar(avatar: MultipartBody.Part) {
        viewModelScope.launch{
            userInfoRepository.updateAvatar(avatar)?.let { newUserInfo ->
                _userInfo.value = newUserInfo
            }
        }
    }

    fun update(newUserInfo: UserInfo) {
        viewModelScope.launch {
            userInfoRepository.update(newUserInfo)?.let { newUserInfo ->
                _userInfo.value = newUserInfo
            }
        }
    }
}
