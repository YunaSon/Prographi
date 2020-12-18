package org.prography.lemorning.utils.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BaseViewModel

abstract class BaseRecyclerPlaceholderAdapter<I, VM : BaseViewModel, B : ViewDataBinding, P : ViewDataBinding>(
    protected var viewmodel : VM,
    protected var placeholderSize : Int = DEFALT_PLACEHOLDER_SIZE,
    var onItemClick: (() -> Unit)? = null
) :
    RecyclerView.Adapter<BaseViewPlaceHolder<I, B, P>>(),
    BaseRecyclerPlaceholderAdapterInterface<I> {

    companion object {
        const val TYPE_PLACEHOLDER : Int = 1
        const val TYPE_REALVIEW : Int = 2
        const val DEFALT_PLACEHOLDER_SIZE : Int = 8
    }

    var items: ArrayList<I?>? = null

    override fun setItem(items: ArrayList<I?>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewPlaceHolder<I, B, P> =
        when (viewType) {
            TYPE_REALVIEW -> object : BaseViewPlaceHolder<I, B, P>(
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false),
                viewType = viewType
            ) {
                override fun bindTo(item: I?) {
                    super.bindTo(item)
                    binding?.root?.setOnClickListener { onItemClick?.invoke() }
                }
            }
            else -> object : BaseViewPlaceHolder<I, B, P>(
                placeholder = DataBindingUtil.inflate(LayoutInflater.from(parent.context), placeholderLayoutId, parent, false),
                viewType = viewType
            ) {}
        }


    override fun onBindViewHolder(holder: BaseViewPlaceHolder<I, B, P>, position: Int) {
        items?.get(position)?.let { holder.bindTo(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items == null) TYPE_PLACEHOLDER else TYPE_REALVIEW
    }

    override fun getItemCount(): Int = if (items == null) placeholderSize else items!!.size

}

interface BaseRecyclerPlaceholderAdapterInterface<I> {
    @get:LayoutRes
    val layoutId: Int

    @get:LayoutRes
    val placeholderLayoutId: Int

    fun setItem(items : ArrayList<I?>)
}