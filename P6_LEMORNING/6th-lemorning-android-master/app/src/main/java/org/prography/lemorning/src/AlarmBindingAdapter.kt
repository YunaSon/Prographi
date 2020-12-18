package org.prography.lemorning.src

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import org.prography.lemorning.src.adapters.AlarmRecyclerAdapter
import org.prography.lemorning.src.adapters.AlarmSettingRecyclerAdapter
import org.prography.lemorning.src.adapters.CardRecyclerAdapter
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.models.Card
import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel


@BindingAdapter(value = ["alarms", "viewModel", "setVM"])
fun settingAdapter(view: RecyclerView, alarms: List<Alarm>?, vm: AlarmDBViewModel, setVM: AlarmViewModel) {
    view.adapter?.run {
        if (this is AlarmRecyclerAdapter) {
            if (alarms != null) {
                this.alarms = alarms
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        if (alarms != null) {
            AlarmRecyclerAdapter(
                alarms,
                vm,
                setVM
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["songs"])
fun settingAdapter(view: RecyclerView, songs: ArrayList<PlaySong?>?) {
    view.adapter?.run {
        if (this is AlarmSettingRecyclerAdapter) {
            if (songs != null) {
                this.songs = songs
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        val animator = view.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        songs?.let {
            AlarmSettingRecyclerAdapter(
                it
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["cards"])
fun settingCardAdapter(view: RecyclerView, cards: ArrayList<Card>?) {
    view.adapter?.run {
        if (this is CardRecyclerAdapter) {
            if (cards != null) {
                this.cards = cards
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        cards?.let {
            CardRecyclerAdapter(
                it
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["setWeek"])
fun setWeek(view:LinearLayout, week:String){
    for(i in week.indices){
        if(week[i] == '1'){
            (view[i] as TextView).setTextColor(Color.parseColor("#000000"))
        }else{
            (view[i] as TextView).setTextColor(Color.parseColor("#bfbfbf"))
        }
    }
}

@BindingAdapter(value = ["text", "subText"])
fun setTextSpan(view:TextView, text:String, subText:String){
    val spannableStringBuilder = SpannableStringBuilder(text + subText)
    spannableStringBuilder.setSpan(StyleSpan(Typeface.BOLD),  0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableStringBuilder.setSpan(RelativeSizeSpan(1.5f),  0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    view.text = spannableStringBuilder
}
