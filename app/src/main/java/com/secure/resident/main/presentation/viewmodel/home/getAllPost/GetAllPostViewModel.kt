package com.secure.resident.main.presentation.viewmodel.home.getAllPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.domain.usecase.home.getAllPost.GetAllPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllPostViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase
) : ViewModel() {
    private val _getAllPosts = MutableStateFlow<ResultState<List<Post>>>(ResultState.Idle)
    val getAllPost : StateFlow<ResultState<List<Post>>> = _getAllPosts.asStateFlow()

    fun getUserPosts(
        token : String
    ) {
        _getAllPosts.value = ResultState.Loading

        viewModelScope.launch {
            val result = getAllPostUseCase(token)

            println(result)

            println(result)
            result.onSuccess { response ->
                _getAllPosts.value = ResultState.Success(response)
            }.onFailure { exception ->
                _getAllPosts.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetAllPostState() {
        _getAllPosts.value = ResultState.Idle
    }
}