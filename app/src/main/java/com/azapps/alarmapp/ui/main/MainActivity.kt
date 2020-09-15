package com.azapps.alarmapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.azapps.alarmapp.R
import com.azapps.alarmapp.data.db.entities.Alarm
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener() { controller, destination, arguments ->
            when (destination.id) {
                R.id.setAlarmFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottom_nav.visibility = View.VISIBLE
                }
                R.id.alarmRingingFragment -> {
                    toolbar.visibility = View.GONE
                    bottom_nav.visibility = View.GONE
                }

            }
        })


        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAlarmRinging().observe(this, Observer { onAlarmRingingChanged(it) })
    }

    private fun onAlarmRingingChanged(alarm : Alarm?) {
        if (alarm != null) {
            navController.navigate(R.id.action_setAlarmFragment_to_alarmRingingFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onDestroy() {
        if (viewModel.getAlarmRinging().value != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        super.onDestroy()
    }
}