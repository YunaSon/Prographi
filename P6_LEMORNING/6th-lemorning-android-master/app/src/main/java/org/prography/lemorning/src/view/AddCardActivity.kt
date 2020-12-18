package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAddCardBinding
import org.prography.lemorning.src.viewmodel.AddCardViewModel

class AddCardActivity(override val layoutId: Int = R.layout.activity_add_card)
    : BaseActivity<ActivityAddCardBinding, AddCardViewModel>() {

    override fun getViewModel(): AddCardViewModel = ViewModelProvider(this).get(AddCardViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }

}