package com.secure.resident.post.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.data.model.CreatePostResponse
import com.secure.resident.post.domain.usecase.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _createPostState = MutableStateFlow<ResultState<CreatePostResponse>>(ResultState.Idle)
    val createPostState : StateFlow<ResultState<CreatePostResponse>> = _createPostState.asStateFlow()

    fun createPost(
        token : String ,
        request : CreatePostRequest
    ) {
        _createPostState.value = ResultState.Loading

        viewModelScope.launch {
            val result = createPostUseCase(token, request)

            println(result)
            result.onSuccess { response ->
                _createPostState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _createPostState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetCreatePostState() { _createPostState.value = ResultState.Idle }
}