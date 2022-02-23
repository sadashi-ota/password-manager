package jp.sadashi.manager.password

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.sadashi.manager.password.data.TestData
import jp.sadashi.manager.password.ui.DetailBody
import jp.sadashi.manager.password.ui.ListBody
import jp.sadashi.manager.password.ui.theme.PasswordmanagerTheme

class PasswordManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordmanagerTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PasswordManagerNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun PasswordManagerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = PasswordManagerScreen.List.name,
        modifier = modifier
    ) {
        composable(PasswordManagerScreen.List.name) {
            ListBody(list = TestData.password) { id ->
                navController.navigate("${PasswordManagerScreen.Detail.name}/${id}")
            }
        }
        composable(
            route = "${PasswordManagerScreen.Detail.name}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            ),
        ) { entry ->
            val id = entry.arguments?.getInt("id")
            val item = TestData.password.find { it.id == id }
                ?: throw IllegalArgumentException("id is not found.")
            DetailBody(password = item)
        }
    }
}
