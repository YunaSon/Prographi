package org.prography.lemorning.src.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmBinding
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.BindingViewHolder
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel

class AlarmRecyclerAdapter(var alarms: List<Alarm> = arrayListOf(), val vm: AlarmDBViewModel, val alarmViewModel: AlarmViewModel): RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmViewHolder>() {
    var flag = false

    class AlarmViewHolder(itemView: View) : BindingViewHolder<ItemAlarmBinding>(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)

        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.binding.alarm = alarm
        holder.binding.vm = vm

        val switch = holder.binding.alarmRecyclerSwitch
        switch.setOnClickListener{
            if((it as SwitchMaterial).isChecked){
                vm.updateOn(alarm, true)
                val context = holder.binding.root.context.applicationContext
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("id", alarm.id)
                    putExtra("songNo", alarm.songNo)
                    putExtra("week", alarm.week)
                    putExtra("date", alarm.date)
                    putExtra("alarmNote", alarm.alarmNote)
                }
                val pendingIntent =
                    alarm.id?.let {alarmId ->
                        PendingIntent.getBroadcast(context,
                            alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                    }
                pendingIntent?.let { it1 ->
                    alarmViewModel.setAlarmManager(alarm,
                        it1, alarmManager)
                }
            }else{
                vm.updateOn(alarm, false)
                vm.cancelAlarm(alarm)
            }
        }

        holder.binding.alarmRecyclerCard.setOnLongClickListener{
            if(flag){
                vm.cancelAlarm(alarm)
                vm.delete(alarm)
                true
            }else false
        }
    }
}