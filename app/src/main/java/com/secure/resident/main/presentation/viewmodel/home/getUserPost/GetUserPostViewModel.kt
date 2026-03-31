package com.secure.resident.main.presentation.viewmodel.home.getUserPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.domain.usecase.home.getUserPost.GetUserPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserPostViewModel @Inject constructor(
    private val getUserPostUseCase: GetUserPostUseCase
) : ViewModel() {

    private val _getUserPosts = MutableStateFlow<ResultState<List<Post>>>(ResultState.Idle)
    val getUserPosts : StateFlow<ResultState<List<Post>>> = _getUserPosts.asStateFlow()

    fun getUserPosts(
        userId : String ,
        token : String
    ) {
        _getUserPosts.value = ResultState.Loading

        viewModelScope.launch {
            val result = getUserPostUseCase(token, userId)

            println(result)
            result.onSuccess { response ->
                _getUserPosts.value = ResultState.Success(response)
            }.onFailure { exception ->
                _getUserPosts.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUserPostState() {
        _getUserPosts.value = ResultState.Idle
    }
}