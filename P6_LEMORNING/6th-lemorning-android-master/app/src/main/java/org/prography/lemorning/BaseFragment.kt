package org.prography.lemorning

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import java.util.*

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(), BaseFragmentView<VM> {

    protected lateinit var binding : B
    protected lateinit var viewmodel : VM

    var mProgressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewmodel = getViewModel()
        binding.setVariable(BR.viewmodel, viewmodel)
        binding.lifecycleOwner = this

        initLocale(context)

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 300
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 300
        }

        initView()

        return binding.root
    }

    override fun initLocale(context : Context?) {
        val localeTag: String? = ApplicationClass.sSharedPreferences.getString(ApplicationClass.LANGUAGE, null)
        if (localeTag != null) {
            Locale.setDefault(Locale.forLanguageTag(localeTag))
            val config =
                Configuration(resources.configuration)
            config.setLocale(Locale.forLanguageTag(localeTag))
            context?.resources?.updateConfiguration(config, context.resources?.getDisplayMetrics())
        }
    }

    open fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(context)
            mProgressDialog!!.setMessage(getString(R.string.loading))
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setIndeterminate(true)
        }
        if (!isRemoving) {
            mProgressDialog!!.show()
        }
    }

    open fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }
}


interface BaseFragmentView<VM : BaseViewModel> {

    @get:LayoutRes
    val layoutId: Int

    fun getViewModel() : VM

    fun initView()

    fun initLocale(context : Context?)
}