package org.prography.lemorning.src.adapters

import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemCategoryBinding
import org.prography.lemorning.databinding.ItemCategoryPlaceholderBinding
import org.prography.lemorning.src.models.Category
import org.prography.lemorning.src.viewmodel.SearchViewModel
import org.prography.lemorning.utils.base.BaseRecyclerPlaceholderAdapter

class CategoryAdapter(
    override val layoutId: Int = R.layout.item_category,
    override val placeholderLayoutId: Int = R.layout.item_category_placeholder,
    viewmodel: SearchViewModel
)
    : BaseRecyclerPlaceholderAdapter<Category, SearchViewModel, ItemCategoryBinding, ItemCategoryPlaceholderBinding>(viewmodel, placeholderSize = 6) {

}