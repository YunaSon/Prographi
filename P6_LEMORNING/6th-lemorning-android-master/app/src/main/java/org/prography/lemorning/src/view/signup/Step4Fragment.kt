package org.prography.lemorning.src.view.signup

import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentStep4SignUpBinding
import org.prography.lemorning.src.viewmodel.SignUpViewModel

class Step4Fragment(override val layoutId: Int = R.layout.fragment_step4_sign_up)
    : BaseFragment<FragmentStep4SignUpBinding, SignUpViewModel>() {

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun initView() {

    }
}