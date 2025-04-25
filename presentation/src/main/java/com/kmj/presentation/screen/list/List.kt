package com.kmj.presentation.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kmj.presentaion.R
import com.kmj.presentation.viewmodel.MainViewModel

@Composable
fun List(
    navController:NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val itemList by viewModel.items.collectAsState()
    val monsterList by viewModel.monsters.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadItems()
        viewModel.loadMonsters()
    }
    Column(
        modifier = Modifier
            .fillMaxSize().navigationBarsPadding()
            .background(Color(0xffffffff))
    ) {
        Header(navController=navController)
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(0f)
            ) {
                unerline()
            }

            Column(
                modifier = Modifier
                    .zIndex(1f)

            ) {
                ListTab(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                Spacer(modifier = Modifier.height(3.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(){
                when (selectedTab) {
                    0 -> {
                            ItemList(
                                items = itemList,
                                navController = navController,
                                viewModel =viewModel,
                            )
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    1 -> {
                            MonsterList(
                                monsters = monsterList,
                                navController = navController,
                                viewModel = viewModel
                            )
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Header(
    navController:NavController
){
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(top=20.dp,start=20.dp,end=20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.backbtn),
                contentDescription = "back",
                modifier = Modifier.size(20.dp).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { navController.popBackStack() },


                )
        }


    }
}

@Composable
fun ListTab(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
){

    Row(
        Modifier
            .fillMaxWidth()
            .clickable (
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){ onTabSelected(0) },
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            Modifier
                .weight(1f)
                .clickable (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){ onTabSelected(0) },
            horizontalArrangement = Arrangement.Center
        ) {
            category(
                text = "아이템",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
        }
        Row(
            Modifier
                .weight(1f)
                .clickable (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){ onTabSelected(1) },
            horizontalArrangement = Arrangement.Center
        ) {
            category(
                text = "몬스터",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
        }
    }
}

@Composable
fun category(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(600),
                color = if (isSelected) Color(0xFF181818) else Color(0xFF9F9F9F),
                textAlign = TextAlign.Center,
                letterSpacing = 0.4.sp,
            ),
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(bottom = 11.dp)
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(color = Color(0xFF181818), shape = RoundedCornerShape(size = 2.dp))
            )
        } else {
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}

@Composable
fun unerline() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .offset(y = (-3).dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf5f5f5))
        )
    }
}