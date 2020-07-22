package com.minibit.myapplication.controller

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View.NO_ID
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.minibit.myapplication.R

class BottomNavController(
    context: Context,
    @IdRes val containerId: Int,
    val containerInitId: Int
) : NavController() {

    private val navigationBackStack = BackStack.of()
    private lateinit var fragmentManager: FragmentManager
    private var listener: OnNavigationItemChanged? = null
    private var navGraphProvider: NavGraphProvider? = null

    init {
        var ctx = context
        while (ctx is ContextWrapper) {
            if (ctx is Activity) {
                fragmentManager = (ctx as FragmentActivity).supportFragmentManager
                break
            }
            ctx = ctx.baseContext
        }
    }

    /**
     * Check in the graph if the item exists
     * If the item does not exist within the navigation stack, it is created and added for the user to see
     * If it exists, get the item from the stack and show it to the user
     */
    override fun onNavigationItemSelected(itemId: Int): Int {
        var tagId = itemId

        if (itemId == NO_ID) {
            tagId = navigationBackStack.last()
        }

        val tagFragment = tagId.toString()

        var navHostFragment = fragmentManager.findFragmentByTag(tagFragment)

        if (navHostFragment == null) {
            val graph = navGraphProvider?.getNavGraphId(tagId)

            if (graph != null) {
                navHostFragment = NavHostFragment.create(graph)
            } else {
                return NO_ID
            }
        }

        replaceContainer(navHostFragment, tagId)

        return tagId
    }

    /**
     * Receive navigation navigators
     */
    override fun graphProvider(navGraphProvider: NavGraphProvider) {
        this.navGraphProvider = navGraphProvider
    }

    /**
     * When the button is pressed
     * should check if you have to return the content of the active navigation itself,
     * if you have no content to return, then you must return to container navigation
     */
    override fun onBackPressed() {
        if (!onBackPressedOnChild()) {
            onBackPressedOnNav()
        }
    }

    /**
     * Checks whether the navigation stack has active fragments
     * When it exists, call the return method of the navigator itself
     * This is keep track of navigation of the component itself
     */
    override fun onBackPressedOnChild(): Boolean {
        val childFragmentManager =
            fragmentManager.findFragmentById(containerId)?.childFragmentManager

        childFragmentManager?.backStackEntryCount?.let {
            if (it > 0) {
                childFragmentManager.fragments.last().findNavController().popBackStack()
                return@onBackPressedOnChild true
            }
        }

        return false
    }

    /**
     * When there are active navigators on the stack, it should be removed completely
     */
    override fun onBackPressedOnNav() {
        if (navigationBackStack.size > 1) {
            navigationBackStack.removeLast()
            listener?.onBackItem(onNavigationItemSelected())
        } else if (
            //the last item not is initial (in this case, reset navigation)
            navigationBackStack.size == 1
            && (navigationBackStack.last() != containerInitId)
        ) {
            onNavigationItemSelected(containerInitId)
            this.listener?.onNavigationReset(containerInitId)
        }
    }

    /**
     * Add listener for onBackPress and change container
     */
    override fun setOnNavigationChanged(listener: OnNavigationItemChanged) {
        this.listener = listener
    }

    /**
     * Each navigation is a host container to stack various fragments,
     * in that case this stack of fragments that are inside that host
     * is obtained and added to the navigation pile to show what the user is seeing
     */
    private fun replaceContainer(navHostFragment: Fragment, tagFragment: Int) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            .replace(containerId, navHostFragment, tagFragment.toString())
            .addToBackStack(null)
            .commit()

        navigationBackStack.moveLast(tagFragment)
    }
}
