package com.secure.resident.onboarding.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.component.SecondaryText
import com.secure.resident.onboarding.navigation.OnBoardingAction
import com.secure.resident.onboarding.presentation.component.NavigationCircle
import com.secure.resident.onboarding.presentation.component.PagerIndicator
import com.secure.resident.onboarding.presentation.helper.onBoardingDataProvider
import com.secure.resident.onboarding.presentation.helper.onBoardingPictureProvider
import kotlinx.coroutines.launch

@Composable
fun OnBoardingView(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Spacer(Modifier.weight(0.4f))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            Image(
                painter = onBoardingPictureProvider(page) ,
                contentDescription = null
            )
        }

        Spacer(Modifier.weight(1f))

        PagerIndicator(
            pageCount = 3,
            currentPage = pagerState.currentPage
        )

        PrimaryText(
            text = onBoardingDataProvider(pagerState.currentPage).title,
            needBold = true
        )

        SecondaryText(
            text = onBoardingDataProvider(pagerState.currentPage).description
        )

        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween ,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            PrimaryText(
                text = "Skip" ,
                needBold = true ,
                modifier = Modifier
                    .clickable {
                        OnBoardingAction.navigationToAuthFlow(navController)
                    }
            )

            NavigationCircle {
                coroutineScope.launch {
                    if (pagerState.currentPage < 2) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        OnBoardingAction.navigationToAuthFlow(navController)
                    }
                }
            }
        }

    }
}
