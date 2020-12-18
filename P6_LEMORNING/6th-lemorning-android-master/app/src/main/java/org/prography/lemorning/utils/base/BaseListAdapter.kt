package org.prography.lemorning.utils.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.prography.lemorning.BaseViewModel

abstract class BaseListAdapter<I, VM : BaseViewModel, B : ViewDataBinding>(diffcallback : DiffUtil.ItemCallback<I>, protected var viewmodel : VM) :
    ListAdapter<I, BaseViewHolder<I, B>>(diffcallback),
    BaseListAdapterInterface {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<I, B> =
        object : BaseViewHolder<I, B>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        ) {}

    override fun onBindViewHolder(holder: BaseViewHolder<I, B>, position: Int) {
        val item: I = getItem(position)
        if (item != null) {
            holder.bindTo(item)
        }
    }
}

interface BaseListAdapterInterface {
    @get:LayoutRes
    val layoutId: Int
}