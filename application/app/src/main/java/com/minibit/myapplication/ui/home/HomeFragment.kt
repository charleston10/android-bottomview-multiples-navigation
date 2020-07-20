package com.minibit.myapplication.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.minibit.myapplication.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            startFoo()
        }
    }

    private fun startFoo() {
        view?.post {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFooFragment2())
        }
    }
}