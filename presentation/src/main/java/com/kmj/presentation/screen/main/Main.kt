package com.kmj.presentation.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kmj.presentaion.R
import com.kmj.presentation.model.ItemModel
import com.kmj.presentation.model.MapModel
import com.kmj.presentation.model.MonsterModel
import com.kmj.presentation.viewmodel.MapViewModel
import com.kmj.presentation.util.Screen
import com.kmj.presentation.viewmodel.MainViewModel
import com.kmj.presentation.viewmodel.NPCViewModel
import java.io.File
import java.net.URI

@Composable
fun Main(
    navController : NavController,
    viewModel: MainViewModel = hiltViewModel(),
    mapviewModel: MapViewModel = hiltViewModel(),
) {

    val itemList by viewModel.items.collectAsState()
    val monsterList by viewModel.monsters.collectAsState()
    val mapList by mapviewModel.maps.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItems()
        viewModel.loadMonsters()
        mapviewModel.loadMaps()

    }

    Log.d("monsterListnpcList",monsterList.toString())



    LazyColumn(
        Modifier.background(color = Color(0xFFffffff)).padding(start=20.dp)
    ) {
        item{
            Spacer(modifier = Modifier.height(50.dp))
            MainSearch()
            Spacer(modifier = Modifier.height(20.dp))
            ItemSection(items = itemList,viewModel = viewModel,navController=navController)
            Spacer(modifier = Modifier.height(30.dp))
            MonsterSection(monsters = monsterList ,viewModel = viewModel, navController=navController)
            Spacer(modifier = Modifier.height(30.dp))
            MapSection(items = mapList ,navController=navController)
        }
    }
}

@Composable
fun MainSearch() {
    var text by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxWidth().padding(end=20.dp)
    ) {
        BasicTextField(
            value = text,
            singleLine = true,
            onValueChange = { text = it },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                textDecoration = TextDecoration.None
            ),

            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF9F9F9F),
                            shape = RoundedCornerShape(size = 48.dp)
                        )
                        .background(color = Color(0xFFffffff), shape = RoundedCornerShape(size = 48.dp))
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.DarkGray,
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Box {
                        if (text.isEmpty()) {
                            Text(
                                text = "Search...",
                                fontSize = 18.sp,
                                color = Color.LightGray,
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )
    }
}

@Composable
fun ItemSection(
    items: List<ItemModel>,
    viewModel : MainViewModel,
    navController:NavController
){
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.fireicon),
                contentDescription = "fireicon",
                modifier = Modifier
                    .size(18.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "사람들이 많이 찾는 아이템",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF181818),

                    letterSpacing = 0.34.sp,
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "전체보기",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF181818),

                    letterSpacing = 0.34.sp,
                ),
                modifier = Modifier.padding(end=20.dp).clickable {
                    navController.navigate(Screen.List.route)
                }
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemList(Item = item , navController=navController)
            }
            item {
                if (items.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        LaunchedEffect(Unit) {
                            viewModel.loadMoreItems()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemList(
    Item : ItemModel,
    navController:NavController
){
    val file = File(URI(Item.ItemImageUrl))
    Column(
        Modifier.clickable {
            navController.navigate(Screen.ItemDetail.createRoute(Item.id))
        }
    ){
        Box(
            Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp).size(70.dp),
            contentAlignment = Alignment.Center,
        ){
            AsyncImage(
                model = file,
                contentDescription = "ItemImageUrl",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = Item.name,
            fontSize = 10.sp,
            color = Color(0xff000000),
            textAlign = TextAlign.Start,
            softWrap = true,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(70.dp)
        )
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = "필요레벨",
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = Item.level.toString(),
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MonsterSection(
    monsters : List<MonsterModel>,
    viewModel : MainViewModel,
    navController : NavController
){
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.fireicon),
                contentDescription = "fireicon",
                modifier = Modifier
                    .size(18.dp)

            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "사람들이 많이 찾는 몬스터",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF181818),
                    letterSpacing = 0.34.sp,
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "전체보기",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF181818),
                    letterSpacing = 0.34.sp,
                ),
                modifier = Modifier.padding(end=20.dp).clickable {
                    navController.navigate("List")
                }
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(monsters) { item ->
                MomsterList(Item = item,navController=navController)
            }
            item {
                if (monsters.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        LaunchedEffect(Unit) {
                            viewModel.loadMoreMonsters()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MomsterList(
    Item : MonsterModel,
    navController:NavController
){
    val file = File(URI(Item.monsterImageUrl))

    Column(
        Modifier.clickable {
            navController.navigate(Screen.MonsterDetail.createRoute(Item.id))
        }
    ){
        Box(
            Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp).size(70.dp),
            contentAlignment = Alignment.Center,

            ){
            AsyncImage(
                model = file,
                contentDescription = "ItemImageUrl",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = Item.name,
            fontSize = 10.sp,
            color = Color(0xff000000),
            textAlign = TextAlign.Start,
            softWrap = true,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(70.dp)
        )
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = "레벨",
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center,
                )
            Spacer(Modifier.width(5.dp))
            Text(
                text = Item.level.toString(),
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MapSection(
    items: List<MapModel>,
    navController:NavController
) {
    Log.d("MapData",items.toString())

    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.fireicon),
                contentDescription = "fireicon",
                modifier = Modifier
                    .size(18.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "지도 맵",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF181818),
                    letterSpacing = 0.34.sp,
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "전체보기",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF181818),
                    letterSpacing = 0.34.sp,
                ),
                modifier = Modifier.padding(end = 20.dp).clickable {
                    navController.navigate(Screen.List.route)
                }
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                MapItem(Item = item, navController = navController)
            }
            item {
                if (items.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapItem(
    Item : MapModel,
    navController:NavController
){

    Column(
        Modifier.clickable {
            navController.navigate(Screen.MapDetail.createRoute(Item.id))
        }
    ){
        Box(
            Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp).size(70.dp),
            contentAlignment = Alignment.Center,

            ){
                Image(
                painter = painterResource(R.drawable.mapleicon),
                contentDescription = "mapleicon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()

            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = Item.name,
            fontSize = 10.sp,
            color = Color(0xff000000),
            textAlign = TextAlign.Start,
            softWrap = true,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(70.dp)

        )
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = Item.streetName,
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
            )
        }
    }
}