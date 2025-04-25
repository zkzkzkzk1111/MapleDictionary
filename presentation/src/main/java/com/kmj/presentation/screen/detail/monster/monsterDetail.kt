package com.kmj.presentation.screen.detail.monster

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kmj.presentaion.R
import com.kmj.presentation.model.MapDetailModel
import com.kmj.presentation.model.MonsterDetailModel
import com.kmj.presentation.screen.detail.item.category
import com.kmj.presentation.screen.list.unerline
import com.kmj.presentation.viewmodel.MonsterDetailViewmodel
import java.io.File
import java.net.URI

@Composable
fun monsterDetail(
    monsterId:Int,
    viewModel: MonsterDetailViewmodel = hiltViewModel(),
    navController: NavController,
) {
    val monsterDetail by viewModel.monsterDetail.collectAsState()
    val monsterMapDetail by viewModel.monsterMapDetails.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        viewModel.loadMonsterDetail(monsterId)

    }

    LaunchedEffect(monsterDetail){
        monsterDetail?.let { viewModel.loadMonsterMapDetail(it.foundAt) }

    }
    Log.d("slfjksalf11",monsterDetail.toString())
    Log.d("slfjksalf",monsterMapDetail.toString())

    LazyColumn(
        Modifier.background(color = Color(0xFFffffff)).padding(horizontal=20.dp)
    ) {
        item{
            Header(navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
            monsterDetail?.let { MonsterDetailSection(Item= it) }
            Spacer(modifier = Modifier.height(40.dp))
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
                    DetailTab(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    Spacer(modifier = Modifier.height(3.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(){
                        when (selectedTab) {
                            0 -> {
                                monsterDetail?.let {
                                    MonsterBasicInfo(
                                        Item = it,
                                        navController = navController,

                                    )
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                            1 -> {
                                monsterDetail?.let {
                                    MonsterStatsInfo(
                                        Item = it,
                                        navController = navController,
                                        MapItem = monsterMapDetail
                                    )
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        }
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
            .fillMaxWidth().padding(top=20.dp)
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
                ) { navController.popBackStack()  },
            )
        }
    }
}

@Composable
fun MonsterDetailSection(
    Item: MonsterDetailModel
){

    val file = File(URI(Item.imageUrl))

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = file,
            contentDescription = "ItemImageUrl",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(color = Color(0xFFefefef), shape = RoundedCornerShape(size = 5.dp)),
            contentAlignment = Alignment.Center
        ){
            Text(text=Item.name)
        }
    }
}

@Composable
fun DetailTab(
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
                text = "기본정보",
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
                text = "상세정보",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
        }
    }
}

@Composable
fun MonsterBasicInfo(
    Item: MonsterDetailModel,
    navController: NavController,
) {
    Column(
        Modifier
            .background(color = Color(0xFFefefef))
            .fillMaxWidth().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {

    }
}

@Composable
fun MonsterStatsInfo(
    Item:MonsterDetailModel,
    navController:NavController,
    MapItem: List<MapDetailModel>
){
    Column(
        Modifier
            .background(color = Color(0xFFefefef))
            .fillMaxWidth().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Item.behavior.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = key)
                Text(text = value.toString())
            }
        }
        Spacer(Modifier.height(5.dp))
        Item.rewards.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(text = key)
                Text(text = value.toString())
            }
        }

        MapItem.forEach{ item->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "${item.streetName}:")
                Text(text = item.name.toString())
            }

        }


    }
}