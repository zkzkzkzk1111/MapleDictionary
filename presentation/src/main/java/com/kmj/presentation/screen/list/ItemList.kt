package com.kmj.presentation.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kmj.presentation.viewmodel.MainViewModel
import com.kmj.presentation.model.ItemModel
import java.io.File
import java.net.URI

@Composable
fun ItemList(
    items: List<ItemModel>,
    navController: NavController,
    viewModel : MainViewModel
){
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        items(items) { item ->
            Item(Item = item)
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

@Composable
fun Item(
    Item : ItemModel,
){
    val file = File(URI(Item.ItemImageUrl))

    Column(
        Modifier.padding(horizontal = 20.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.background(color = Color(0xFFFFF7CC)).padding(15.dp).size(70.dp),
                contentAlignment = Alignment.Center,

                ) {
                AsyncImage(
                    model = file,
                    contentDescription = "ItemImageUrl",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(Modifier.width(15.dp))
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
        }
    }
}