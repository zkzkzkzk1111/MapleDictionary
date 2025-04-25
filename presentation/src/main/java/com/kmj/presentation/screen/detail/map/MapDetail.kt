package com.kmj.presentation.screen.detail.map

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
import androidx.compose.foundation.layout.width
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
import com.kmj.presentation.viewmodel.MonsterDetailViewmodel
import com.kmj.presentation.screen.list.unerline
import com.kmj.presentation.util.Screen
import com.kmj.presentation.viewmodel.MapViewModel
import com.kmj.presentation.viewmodel.NPCViewModel

@Composable
fun MapDetail(
    mapId :Int,
    navController:NavController,
    viewModel : MapViewModel = hiltViewModel(),
    monsterViewModel : MonsterDetailViewmodel = hiltViewModel(),
    npcviewModel : NPCViewModel = hiltViewModel()

) {

    val mapDetail by viewModel.mapDetail.collectAsState()
    val mapMonsterDetail by monsterViewModel.mapmonsterMapDetails.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val npc by npcviewModel.npc.collectAsState()

    LaunchedEffect(Unit){
        viewModel.loadMapDetail(mapId)
        npcviewModel.loadNPCs()
    }

    Log.d("dsjflsdfkjsdklf",npc.toString())
    LaunchedEffect(mapDetail){
        mapDetail?.let { monsterViewModel.loadMapMonsterDetail(it.mobs) }
    }

    Log.d("mapmonsterdata",mapMonsterDetail.toString())

    LazyColumn(
        Modifier.background(color = Color(0xFFffffff)).padding(horizontal=20.dp)
    ) {
        item{
            Header(navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
            mapDetail?.let { MapDetailSection(Item= it) }
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
                    MapDetailTab(
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
                                mapMonsterDetail.let {
                                    MapMonsterInfo(
                                        Item = it,
                                        navController = navController,
                                        )
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                            1 -> {
                                MapNPCInfo(
                                    navController = navController,
                                )
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
    navController: NavController
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
fun MapDetailSection(
    Item: MapDetailModel
){
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.mapleicon),
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

            Text(text="${Item.name} : ${Item.streetName}")


        }
    }
}

@Composable
fun MapMonsterInfo(
    Item: List<MonsterDetailModel>,
    navController: NavController,
) {

    Item.forEach{ monsterDetail ->
        Column(
            Modifier
                .background(color = Color(0xFFefefef))
                .fillMaxWidth().padding(20.dp).clickable {
                    navController.navigate(Screen.MonsterDetail.createRoute(monsterDetail.id))
                },
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                AsyncImage(
                    model = monsterDetail.imageUrl,
                    contentDescription = "ItemImageUrl",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(70.dp)
                )
                Spacer(Modifier.width(20.dp))
                Text(text=monsterDetail.name)
            }
        }
    }
}

@Composable
fun MapNPCInfo(

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
fun MapDetailTab(
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
                text = "몬스터 정보",
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
                text = "NPC정보",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
        }
    }
}