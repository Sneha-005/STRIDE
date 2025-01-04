package com.example.stride.domain.sharedModels

import com.example.stride.domain.models.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository@Inject constructor() {
    val  _data= MutableStateFlow(UserModel())
    val data: StateFlow<UserModel> = _data.asStateFlow()

    fun setValues(userModel: UserModel) {
        _data.value = userModel
    }
    fun getValues(): UserModel {
        return _data.value
    }


}