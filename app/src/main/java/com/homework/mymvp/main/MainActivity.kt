package com.homework.mymvp.main

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.homework.mymvp.core.App
import com.homework.mymvp.R
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {


    private val navigator = AppNavigator(this, R.id.container)
    private lateinit var binding: ActivityMainBinding
    private var app = App()

    private val presenter by moxyPresenter {
    MainPresenter(app.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        app.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        app.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { currentFragment ->
            if (currentFragment is OnBackPressedListener && currentFragment.onBackPressed()) {
                return
            }
        }
        presenter.onBackPressed()
    }
}