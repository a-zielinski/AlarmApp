package com.azapps.alarmapp.ui.main.alarmringing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azapps.alarmapp.R
import com.azapps.alarmapp.data.db.entities.Alarm
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_alarm_ringing.*
import javax.inject.Inject


class AlarmRingingFragment : DaggerFragment() {
    private lateinit var viewModel: AlarmRingingViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm_ringing, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AlarmRingingViewModel::class.java)
        viewModel.getAlarmRinging.observe(viewLifecycleOwner, Observer { onAlarmRinging(it) })


        bt_stop_alarm.setOnClickListener {
            viewModel.stopAlarm()
            findNavController().popBackStack()

        }
    }

    private fun onAlarmRinging(alarm: Alarm) {
        tv_alarm_ringing.text = "${alarm.title} ${alarm.id}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        // The callback can be enabled or disabled here or in handleOnBackPressed()

    }


    private fun onNewToastObserved(toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
}