package jp.sadashi.manager.password.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.findNavControllerById(@IdRes id: Int): NavController {
    val fragment = supportFragmentManager.findFragmentById(id) as? NavHostFragment
        ?: throw IllegalStateException("Not found fragment. id:$id")
    return fragment.navController
}
