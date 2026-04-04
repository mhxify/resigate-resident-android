package com.secure.resident.reserveFacility.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.reserveFacility.data.local.ReserveFacilityPrefs
import com.secure.resident.reserveFacility.navigation.ReserveFacilitiesAction
import com.secure.resident.reserveFacility.presentation.view.component.FacilityItemView
import com.secure.resident.reserveFacility.presentation.viewmodel.getfacilities.GetFacilitiesViewModel

@Composable
fun SelectFacilityScreen(
    navController: NavController ,
    getFacilitiesViewModel: GetFacilitiesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val token = remember { AuthPrefs.getToken(context) }

    val getFacilityState by getFacilitiesViewModel.facilityState.collectAsState()

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            getFacilitiesViewModel.getFacility(token)
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Select Facility"
            )
        }
    ) { innerPadding ->
        when(val state = getFacilityState) {

            is ResultState.Loading -> {
                CircularIndicator()
            }

            is ResultState.Success -> {

                if (state.data.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding) ,
                        contentAlignment = Alignment.Center
                    ) {
                        PrimaryText(
                            "No Facilities available for reservation" ,
                            needBold = true
                        )
                    }
                }else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { facility ->
                            FacilityItemView(
                                facility = facility,
                                onClick = {
                                    ReserveFacilityPrefs.saveFacilityId(
                                        facility.facilityId ,
                                        context
                                    )
                                    ReserveFacilitiesAction.navigationToCompleteFacility(navController)
                                }
                            )
                        }
                    }
                }
            }

            is ResultState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) ,
                    contentAlignment = Alignment.Center
                ) {
                    PrimaryText(
                        state.message ,
                        needBold = true
                    )
                }
            }

            else -> null
        }
    }
}