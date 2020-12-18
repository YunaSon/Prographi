package org.prography.lemorning.src.view.signup

import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentStep1SignUpBinding
import org.prography.lemorning.src.viewmodel.SignUpViewModel

class Step1Fragment(override val layoutId: Int = R.layout.fragment_step1_sign_up)
    : BaseFragment<FragmentStep1SignUpBinding, SignUpViewModel>() {

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun initView() {

    }
}