package com.azapps.alarmapp.ui.main.setalarm

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azapps.alarmapp.R
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.receivers.AlarmBroadcastReceiver
import com.azapps.alarmapp.internal.AppConstants
import com.azapps.alarmapp.ui.main.setalarm.adapter.AlarmAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_set_alarm.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import java.util.*
import javax.inject.Inject


class SetAlarmFragment : DaggerFragment() {
    private lateinit var adapter: AlarmAdapter;
    private lateinit var viewModel: SetAlarmViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_set_alarm, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = AlarmAdapter(requireContext(), ::onAlarmDeleted)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SetAlarmViewModel::class.java)

        setViewModelObservers()

        button_new_alarm.setOnClickListener { setNewAlarm() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNewAlarm() {

        val newCalendar = Calendar.getInstance();
        val startTime = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth);
                TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        newDate.set(Calendar.MINUTE, minute);

                        val z: ZonedDateTime = ZonedDateTime.of(
                            year, monthOfYear+1, dayOfMonth,
                            hourOfDay, minute, 0, 0,
                            ZoneId.systemDefault()
                        )

                        viewModel.setAlarm(
                            "My Alarm", LocalDateTime.from(z).minusHours(2)
                        )

                    },
                    newCalendar.get(Calendar.HOUR_OF_DAY),
                    newCalendar.get(Calendar.MINUTE)+1,
                    true
                ).show();

            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        startTime.show()
    }

    private fun setViewModelObservers() {
        viewModel.observeNewToast.observe(viewLifecycleOwner, Observer {
            onNewToastObserved(it)
        })

        viewModel.observeStartNewAlarmEvent.observe(viewLifecycleOwner, Observer {
            onStartNewAlarmObserved(it)
        })

        viewModel.getAllAlarms().observe(viewLifecycleOwner, Observer {
            adapter.setAlarms(it)
        })

    }

    private fun onAlarmDeleted(alarm: Alarm) {
        viewModel.turnAlarmOff(alarm)
    }


    private fun onNewToastObserved(toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun onStartNewAlarmObserved(alarm: Alarm) {

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            ?: return

        val startTime = alarm.startTime.toEpochSecond(ZoneOffset.UTC) * 1000
        val alarmIntent = Intent(requireContext(), AlarmBroadcastReceiver::class.java)
            .also { it.putExtra(AppConstants.INTENT_ALARM_ID, alarm.id) }

        val displayIntent =
            PendingIntent.getBroadcast(requireContext(), alarm.id, alarmIntent, 0);

        if (System.currentTimeMillis() < startTime) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                startTime,
                displayIntent
            )
        }
    }


}