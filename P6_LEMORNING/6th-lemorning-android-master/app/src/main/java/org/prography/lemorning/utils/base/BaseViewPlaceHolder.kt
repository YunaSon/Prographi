package org.prography.lemorning.utils.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BR

abstract class BaseViewPlaceHolder<I, B : ViewDataBinding, P : ViewDataBinding>(var binding : B? = null, var placeholder : P? = null, viewType : Int)
    : RecyclerView.ViewHolder(
    when (viewType) {
        BaseRecyclerPlaceholderAdapter.TYPE_REALVIEW -> binding!!.root
        BaseRecyclerPlaceholderAdapter.TYPE_PLACEHOLDER -> placeholder!!.root
        else -> placeholder!!.root
    }
), BaseBindingViewPlaceHolder<I> {

    open fun bindTo(item: I?) {
        binding?.let {
            it.setVariable(BR.item, item)
            initItem(item)
        }
        placeholder?.let {
            initPlaceholder()
        }
    }

    override fun initItem(item: I?) {}

    override fun initPlaceholder() {}
}

interface BaseBindingViewPlaceHolder<I> {
    fun initItem(item: I?)

    fun initPlaceholder()
}