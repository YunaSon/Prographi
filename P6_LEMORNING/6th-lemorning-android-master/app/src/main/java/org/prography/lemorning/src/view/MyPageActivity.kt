package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityMypageBinding
import org.prography.lemorning.src.viewmodel.MyPageViewModel

class MyPageActivity(override val layoutId: Int = R.layout.activity_mypage)
    : BaseActivity<ActivityMypageBinding, MyPageViewModel>() {

    override fun getViewModel(): MyPageViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.layoutMusicMypage.setOnClickListener {
            startActivity(Intent(this@MyPageActivity, CreateSongActivity::class.java))
        }

        binding.ivCloseMypage.setOnClickListener { finish() }

        binding.tvMyCardMypage.setOnClickListener {
            startActivity(Intent(this@MyPageActivity, CardActivity::class.java))
        }
    }
}