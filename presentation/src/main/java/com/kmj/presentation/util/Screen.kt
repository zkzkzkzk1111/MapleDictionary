package com.kmj.presentation.util

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object List : Screen("list")
    object ItemDetail : Screen("item_detail/{itemId}") {
        fun createRoute(itemId: Int) = "item_detail/$itemId"
    }

    object MonsterDetail : Screen("monster_detail/{monsterId}") {
        fun createRoute(monsterId: Int) = "monster_detail/$monsterId"
    }

    object MapDetail : Screen("map_detail/{mapId}") {
        fun createRoute(mapId: Int) = "map_detail/$mapId"
    }
}
