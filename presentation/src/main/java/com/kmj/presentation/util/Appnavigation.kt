package com.kmj.presentation.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kmj.presentation.screen.detail.item.ItemDetail
import com.kmj.presentation.screen.detail.monster.monsterDetail
import com.kmj.presentation.screen.detail.map.MapDetail
import com.kmj.presentation.screen.main.Main

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ){
        composable(Screen.Main.route){
            Main(navController = navController)
        }
        composable(Screen.List.route){
            com.kmj.presentation.screen.list.List(navController = navController)
        }
        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            ItemDetail(itemId = itemId, navController = navController)
        }

        composable(
            route = Screen.MonsterDetail.route,
            arguments = listOf(navArgument("monsterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getInt("monsterId") ?: -1
            monsterDetail(monsterId = monsterId, navController = navController)
        }

        composable(
            route = Screen.MapDetail.route,
            arguments = listOf(navArgument("mapId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mapId = backStackEntry.arguments?.getInt("mapId") ?: -1
            MapDetail(mapId = mapId, navController = navController)
        }
    }
}