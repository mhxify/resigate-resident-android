package com.secure.resident.main.presentation.viewmodel.chat.getUserGroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.usecase.chat.getGroups.GetUserGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserGroupViewModel @Inject constructor(
    private val groupMessageUseCase: GetUserGroupUseCase
) : ViewModel() {

    private val _getGroupsState = MutableStateFlow<ResultState<List<GroupMessage>>>(ResultState.Idle)
    val getGroupsState : StateFlow<ResultState<List<GroupMessage>>> = _getGroupsState.asStateFlow()

    fun getUserGroups(
        userId : String ,
        token : String
    ) {
        _getGroupsState.value = ResultState.Loading

        viewModelScope.launch {
            val result = groupMessageUseCase(token, userId)

            println(result)
            result.onSuccess { response ->
                _getGroupsState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _getGroupsState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetGroupState() {
        _getGroupsState.value = ResultState.Idle
    }
}