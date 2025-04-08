package com.kmj.presentation.main

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kmj.presentaion.R
import com.kmj.presentation.model.ItemModel
import com.kmj.presentation.util.LocalCustomImageLoader
import java.io.File
import java.net.URI

@Composable
fun Main(
    viewModel: MainViewModel = hiltViewModel()
) {

    val itemList by viewModel.items.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    LazyColumn(
        Modifier.background(color = Color(0xFFffffff))
    ) {
        item{
            MainSearch()
            Spacer(modifier = Modifier.height(20.dp))
            ItemSection(items = itemList)
            Spacer(modifier = Modifier.height(30.dp))
            MonsterSection()
        }
    }
}

@Composable
fun MainSearch() {
    var text by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxWidth()
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
    items: List<ItemModel>
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
                )
            )

        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemList(Item = item)
            }
        }
    }
}

@Composable
fun ItemList(
    Item : ItemModel
){

    val file = File(URI(Item.ItemImageUrl))
    val bitmap = remember {
        BitmapFactory.decodeFile(file.absolutePath)
    }

    Column(){
        Box(
            Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp),
            contentAlignment = Alignment.Center,

        ){
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "ItemImageUrl",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
            )
        }
        Spacer(Modifier.height(5.dp))

        Text(
            text = Item.name,
            fontSize = 10.sp,
            color = Color(0xff000000),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = "필요레벨",
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
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
fun MonsterSection(){
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
                )
            )

        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item{
                MomsterList()
            }
        }

    }
}

@Composable
fun MomsterList(){
    Column(){
        Box(
            Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp),
            contentAlignment = Alignment.Center,

            ){
            Image(
                painter = painterResource(R.drawable.fireicon),
                contentDescription = "itemimg",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)

            )
        }
        Spacer(Modifier.height(5.dp))

        Text(
            text = "이름",
            fontSize = 10.sp,
            color = Color(0xff000000),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = "필요레벨",
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
            )
            Text(
                text = "10",
                fontSize = 10.sp,
                color = Color(0xff000000),
                textAlign = TextAlign.Center
            )
        }

    }

}