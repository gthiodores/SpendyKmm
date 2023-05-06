package com.gthio.spendykmm.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gthio.spendykmm.android.add.SpendingAddRoute
import com.gthio.spendykmm.android.list.SpendingListRoute

@Composable
fun AppNavigator(controller: NavHostController = rememberNavController()) {
    NavHost(navController = controller, startDestination = "list") {
        composable("list") {
            SpendingListRoute(
                toAdd = { controller.navigate("add") },
                toEdit = { controller.navigate("add") }, // TODO: Add edit screen
            )
        }

        composable("add") {
            SpendingAddRoute(onBackRequest = controller::popBackStack)
        }
    }
}