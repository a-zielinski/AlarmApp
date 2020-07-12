package com.azapps.momapp.ui.alarm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.azapps.momapp.AppComponent
import com.azapps.momapp.DaggerAppComponent
import com.azapps.momapp.R
import javax.inject.Inject

class AlarmFragment : Fragment() {
    private lateinit var viewModel: AlarmViewModel

    @Inject
    lateinit var viewModelFactory: AlarmViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alarm_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        val component: AppComponent = DaggerAppComponent.builder().build()
        component.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AlarmViewModel::class.java)
    }

}