package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentAlarmBinding
import org.prography.lemorning.src.adapters.AlarmRecyclerAdapter
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmFragment: Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AlarmTheme);
        val localInflater = inflater.cloneInContext(contextThemeWrapper);

        val binding = DataBindingUtil.inflate<FragmentAlarmBinding>(localInflater, R.layout.fragment_alarm, container, false)
        binding.alarmViewModel = ViewModelProvider(this)
            .get(AlarmDBViewModel::class.java)
        binding.dateVM = ViewModelProvider(this)
            .get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this

        binding.alarmEditButton.setOnClickListener(this)
        binding.alarmAdd.setOnClickListener(this)
        binding.alarmImage.setOnClickListener { startActivity(Intent(activity, MyPageActivity::class.java)) }

        return binding.root
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.alarm_edit_button -> {
                if(alarm_edit_button.text == "삭제"){
                    alarm_edit_button.text = "관리"
                    (alarm_recycler.adapter as AlarmRecyclerAdapter).flag = false
//                    alarm_delete_check.visibility = View.INVISIBLE
                }else{
                    alarm_edit_button.text = "삭제"
                    (alarm_recycler.adapter as AlarmRecyclerAdapter).flag = true
//                    alarm_delete_check.visibility = View.VISIBLE
                    Toast.makeText(context, "삭제할 알람을 길게 클릭해주세요", Toast.LENGTH_LONG).show()
                }
            }
            R.id.alarm_add -> {
                val intent = Intent(
                    context,
                    AlarmSettingActivity::class.java
                )
                startActivity(intent)
            }
        }
    }
}