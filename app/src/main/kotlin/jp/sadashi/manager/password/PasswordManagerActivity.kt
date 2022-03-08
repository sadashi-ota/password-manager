package jp.sadashi.manager.password

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.sadashi.manager.password.data.TestData
import jp.sadashi.manager.password.ui.DetailBody
import jp.sadashi.manager.password.ui.ListBody
import jp.sadashi.manager.password.ui.theme.PasswordManagerTheme

@ExperimentalMaterialApi
class PasswordManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Password Manager") }) },
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            icon = { Icon(Icons.Filled.Add, "") },
                            text = { Text("Add") },
                            onClick = {
                                navController.navigate(PasswordManagerScreen.Detail.name)
                            },
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        )
                    },
                ) {
                    PasswordManagerNavHost(navController)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PasswordManagerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = PasswordManagerScreen.List.name,
        modifier = modifier
    ) {
        composable(PasswordManagerScreen.List.name) {
            ListBody(records = TestData.PASSWORD_RECORDS) { id ->
                navController.navigate("${PasswordManagerScreen.Detail.name}/${id}")
            }
        }
        composable(PasswordManagerScreen.Detail.name) {
            DetailBody()
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
            val item = TestData.PASSWORD_RECORDS.find { it.id == id }
                ?: throw IllegalArgumentException("id is not found.")
            DetailBody(record = item)
        }
    }
}
