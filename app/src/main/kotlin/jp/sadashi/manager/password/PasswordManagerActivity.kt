package jp.sadashi.manager.password

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.sadashi.manager.password.theme.PasswordManagerTheme

@ExperimentalAnimationApi
@ExperimentalMaterialApi
class PasswordManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                val navController = rememberAnimatedNavController()
                PasswordManagerNavHost(navController)
            }
        }
    }
}

