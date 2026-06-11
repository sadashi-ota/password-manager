package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.passwordmanager.feature.lock.LockViewModel
import com.example.passwordmanager.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val lockViewModel: LockViewModel by viewModels()
    private var lastBackgroundTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ProcessLifecycleObserver to lock app after 10 minutes in background
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    lastBackgroundTime = System.currentTimeMillis()
                }
                Lifecycle.Event.ON_START -> {
                    val now = System.currentTimeMillis()
                    // 10 minutes = 10 * 60 * 1000 = 600,000 ms
                    if (lockViewModel.isUnlocked.value && lastBackgroundTime > 0L && (now - lastBackgroundTime) >= 10 * 60 * 1000) {
                        lockViewModel.lock()
                    }
                }
                else -> {}
            }
        })

        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainNavigation(lockViewModel = lockViewModel)
                }
            }
        }
    }
}
