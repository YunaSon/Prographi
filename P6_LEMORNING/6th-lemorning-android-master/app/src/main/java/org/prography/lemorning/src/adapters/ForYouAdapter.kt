package org.prography.lemorning.src.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemForyouBinding
import org.prography.lemorning.databinding.ItemForyouPlaceholderBinding
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.base.BaseRecyclerPlaceholderAdapter
import org.prography.lemorning.utils.base.BaseViewPlaceHolder

class ForYouAdapter(
    override val layoutId: Int = R.layout.item_foryou,
    override val placeholderLayoutId: Int = R.layout.item_foryou_placeholder,
    viewModel: TrendingViewModel
) : BaseRecyclerPlaceholderAdapter<ForYou, TrendingViewModel, ItemForyouBinding, ItemForyouPlaceholderBinding>(viewModel) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewPlaceHolder<ForYou, ItemForyouBinding, ItemForyouPlaceholderBinding> {
        return when (viewType) {
            TYPE_REALVIEW -> object : BaseViewPlaceHolder<ForYou, ItemForyouBinding, ItemForyouPlaceholderBinding>(
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false),
                viewType = viewType
            ) {
                override fun initItem(item: ForYou?) {
                    super.initItem(item)
                    binding?.root?.setOnClickListener { item?.let { viewmodel.onClickSong(it.no, null) } }
                }
            }
            else -> object : BaseViewPlaceHolder<ForYou, ItemForyouBinding, ItemForyouPlaceholderBinding>(
                placeholder = DataBindingUtil.inflate(LayoutInflater.from(parent.context), placeholderLayoutId, parent, false),
                viewType = viewType
            ) {}
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = ForYouLayoutManager(recyclerView.context)
    }

    class ForYouLayoutManager(context : Context)
        : LinearLayoutManager(context, HORIZONTAL, false) {
        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ): Int {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val startCenterPoint = getChildAt(0)?.let { it.width / 2f + paddingStart } ?: 30f
            val d1 = 0.8f * startCenterPoint
            for (i in 0 until childCount) {
                val child : View = getChildAt(i)!!
                val childMidpoint = child.let { (getDecoratedLeft(it) + getDecoratedRight(it)) / 2f}
                val d = Math.min(d1, Math.abs(startCenterPoint - childMidpoint))
                val scaleFactor =  (d - 0.8f) / (d1 - 0.8f)
                child.apply {
                    pivotX =
                        if (childMidpoint / startCenterPoint > 1f)
                            child.width.toFloat()
                        else if (childMidpoint / startCenterPoint < -1f)
                            0f
                        else
                            0.5f * (childMidpoint / startCenterPoint) * child.width
                    pivotY = height / 2f
                    scaleX = 1 - 0.2f * scaleFactor
                    scaleY = 1 - 0.2f * scaleFactor
                }
            }
            return scrolled
        }

        override fun onLayoutChildren(
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ) {
            super.onLayoutChildren(recycler, state)
            scrollHorizontallyBy(0, recycler, state)
        }


    }
}

