package jp.sadashi.manager.password

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import jp.sadashi.manager.password.data.TestData
import jp.sadashi.manager.password.ui.DetailScreen
import jp.sadashi.manager.password.ui.ListScreen

enum class PasswordManagerScreen {
    List,
    Detail,
    ;
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PasswordManagerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    AnimatedNavHost(
        navController = navController,
        startDestination = PasswordManagerScreen.List.name,
        modifier = modifier
    ) {
        composable(PasswordManagerScreen.List.name) {
            ListScreen(navController = navController, records = TestData.PASSWORD_RECORDS)
        }
        composable(
            PasswordManagerScreen.Detail.name,
            enterTransition = {
                scaleIn(animationSpec = tween(500))
            },
            exitTransition = {
                scaleOut(animationSpec = tween(500))
            },
        ) {
            DetailScreen(navController = navController)
        }
        composable(
            route = "${PasswordManagerScreen.Detail.name}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                scaleIn(animationSpec = tween(500))
            },
            exitTransition = {
                scaleOut(animationSpec = tween(500))
            },
        ) { entry ->
            val id = entry.arguments?.getInt("id")
            val item = TestData.PASSWORD_RECORDS.find { it.id == id }
                ?: throw IllegalArgumentException("id is not found.")
            DetailScreen(navController = navController, record = item)
        }
    }
}

@Composable
fun PasswordManagerAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    TopAppBar(
        title = { Text("Password Manager") },
        elevation = 10.dp,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    )
}