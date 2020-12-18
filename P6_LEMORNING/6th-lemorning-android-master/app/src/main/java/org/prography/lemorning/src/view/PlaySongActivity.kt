package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.google.firebase.crashlytics.internal.common.CrashlyticsReportDataCapture
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.BuildConfig
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityPlaySongBinding
import org.prography.lemorning.src.viewmodel.PlaySongViewModel
import org.prography.lemorning.utils.Converters
import org.prography.lemorning.utils.components.SimpleMessageDialog

class PlaySongActivity(override val layoutId: Int = R.layout.activity_play_song)
    : BaseActivity<ActivityPlaySongBinding, PlaySongViewModel>() {

    override fun getViewModel(): PlaySongViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, PlaySongViewModelFactory(songNo)).get(PlaySongViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {

        /* 추천 RecyclerView */
        binding.rvRecommendPlaySong.adapter = viewmodel.playRecommendAdapter
        viewmodel.nextSongList.observe(this, Observer { viewmodel.playRecommendAdapter.setItem(it) })

        binding.visualizerPlaySong.setColor(getColor(R.color.colorPrimary))

        /* Alarm Play & Stop */
        binding.ivPlayPlaySong.setOnClickListener { viewmodel.mediaPlayer.let { it.value?.let {
            if (it.isPlaying) {
                it.pause()
                binding.ivPlayPlaySong.setImageResource(R.drawable.ic_play_big)
            } else {
                it.start()
                binding.ivPlayPlaySong.setImageResource(R.drawable.ic_pause)
            }
        } } }
        binding.sdSeekbarPlaySong.addOnChangeListener { _, value, fromUser -> if (fromUser) viewmodel.mediaPlayer.value?.seekTo(value.toInt()) }

        viewmodel.mediaPlayer.observe(this,  Observer {
            /* Audio Visualizer */
            it?.setOnPreparedListener {
                //binding.visualizerPlaySong.setPlayer(it.audioSessionId)
                binding.sdSeekbarPlaySong.valueTo = it.duration.toFloat()
                binding.sdSeekbarPlaySong.valueFrom = it.currentPosition.toFloat()
                binding.tvEndPlaySong.text = Converters.mediaPositionToRealTimeTxt(it.duration)
                it.start()
                viewmodel.registerTimerOnMediaPlayer(it)
            }
        })

        /* Set On Click Listener */
        binding.ivClosePlaySong.setOnClickListener { finish() }
        binding.ivMorePlaySong.setOnClickListener { SimpleMessageDialog(
            context = this,
            message = getString(R.string.coming_soon)
        ).show() }
        binding.ivLikePlaySong.setOnClickListener { SimpleMessageDialog(
            context = this,
            message = getString(R.string.coming_soon)
        ).show() }

    }

    override fun onPause() {
        super.onPause()
        if (!BuildConfig.DEBUG) {
            viewmodel.mediaPlayer.value?.let { if (it.isPlaying) it.pause() }
        }
    }


    override fun onDestroy() {
        viewmodel.mediaPlayer.value?.release()
        viewmodel.mediaPlayer.value = null
        super.onDestroy()
    }

    class PlaySongViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlaySongViewModel(songNo) as T
    }
}