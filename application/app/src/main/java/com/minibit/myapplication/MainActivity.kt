package com.minibit.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.minibit.myapplication.controller.BottomNavController
import com.minibit.myapplication.controller.setUpNavigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main),
    BottomNavController.NavGraphProvider {

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(this, R.id.container)
    }

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(this, R.id.container, R.id.navigation_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavController.setNavGraphProvider(this)
        bottom_navigation.setUpNavigation(bottomNavController)
        if (savedInstanceState == null) bottomNavController
            .onNavigationItemSelected()
    }

    override fun getNavGraphId(itemId: Int): Int {
        return when (itemId) {
            R.id.navigation_home -> R.navigation.home_navigation
            R.id.navigation_dashboard -> R.navigation.dashboard_navigation
            R.id.navigation_notifications -> R.navigation.notifications_navigation
            else -> R.navigation.home_navigation
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController
        .navigateUp()

    override fun onBackPressed() = bottomNavController.onBackPressed()
}