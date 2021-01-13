package jp.sadashi.manager.password

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import jp.sadashi.manager.password.databinding.ActivityMainBinding
import jp.sadashi.manager.password.extensions.findNavControllerById

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy { findNavControllerById(R.id.nav_host_fragment) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.PasswordListFragment) {
                binding.fab.hide()
            } else {
                binding.fab.show()
            }
        }

        binding.fab.setOnClickListener {
            val action =
                PasswordListFragmentDirections.actionPasswordListFragmentToPasswordDetailFragment()
            navController.navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
