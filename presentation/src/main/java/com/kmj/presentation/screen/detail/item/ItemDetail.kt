package com.kmj.presentation.screen.detail.item

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kmj.presentaion.R
import com.kmj.presentation.model.ItemDetailModel
import com.kmj.presentation.screen.list.unerline
import com.kmj.presentation.viewmodel.ItemDetailViewmodel
import java.io.File
import java.net.URI

@Composable
fun ItemDetail(
    itemId:Int,
    viewModel: ItemDetailViewmodel = hiltViewModel(),
    navController:NavController,
) {
    val itemDetail by viewModel.itemDetail.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        viewModel.loadItemDetail(itemId)
    }

    LazyColumn(
        Modifier.background(color = Color(0xFFffffff)).padding(horizontal=20.dp)
    ) {
        item{
            Header(navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
            itemDetail?.let { ItemDetailSection(Item= it) }
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
                                itemDetail?.let {
                                    ItemBasicInfo(
                                        Item = it,
                                        navController = navController,

                                        )
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                            1 -> {
                                itemDetail?.let {
                                    ItemStatsInfo(
                                        Item = it,
                                        navController = navController,
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
fun ItemDetailSection(
    Item:ItemDetailModel
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
fun ItemBasicInfo(
    Item:ItemDetailModel,
    navController: NavController,
){
    Column(
        Modifier.background(color=Color(0xFFefefef)).fillMaxWidth().padding(20.dp),

    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
        Text(text="레벨")
        Text(text=Item.level.toString())
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text="가격")
            Text(text=Item.price.toString())
        }
    }
}

@Composable
fun ItemStatsInfo(
    Item:ItemDetailModel,
    navController:NavController,
){
    Column(
        Modifier
            .background(color = Color(0xFFefefef))
            .fillMaxWidth().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {
        Item.stats.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(text = key)
                Text(text = value.toString())
            }
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