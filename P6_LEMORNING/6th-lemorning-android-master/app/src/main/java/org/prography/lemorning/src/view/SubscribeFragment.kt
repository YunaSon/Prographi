package org.prography.lemorning.src.view

import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentSubscribeBinding
import org.prography.lemorning.src.viewmodel.SubscribeViewModel

class SubscribeFragment(override val layoutId: Int = R.layout.fragment_subscribe)
    : BaseFragment<FragmentSubscribeBinding, SubscribeViewModel>() {

    override fun getViewModel(): SubscribeViewModel = ViewModelProvider(this).get(SubscribeViewModel::class.java)

    override fun initView() {

    }
}